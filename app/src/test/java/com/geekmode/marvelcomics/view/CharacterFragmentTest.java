package com.geekmode.marvelcomics.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekmode.marvelcomics.BaseInjectedRobolectricTest;
import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.Character;
import com.geekmode.marvelcomics.model.ListWrapper;
import com.geekmode.marvelcomics.model.ResponseWrapper;
import com.geekmode.marvelcomics.services.CharacterService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.Collections;
import java.util.List;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CharacterFragmentTest extends BaseInjectedRobolectricTest {

    private final PublishSubject<ResponseWrapper<Character>> responseObservable = PublishSubject.create();
    @Mock
    private CharacterService characterService;
    @Mock
    private ImageUtil imageLoader;
    @Mock
    private SchedulerProvider schedulerProvider;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageView characterImageView;

    @Before
    public void setUp() throws Exception {
        when(schedulerProvider.getMainScheduler()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.getIoScheduler()).thenReturn(Schedulers.immediate());

        final int expectedId = 123;
        when(characterService.characterById(String.valueOf(expectedId))).thenReturn(responseObservable);

        final Fragment testObject = CharacterFragment.newInstance(expectedId);
        SupportFragmentTestUtil.startFragment(testObject);

        nameTextView = (TextView) testObject.getView().findViewById(R.id.title_text_view);
        descriptionTextView = (TextView) testObject.getView().findViewById(R.id.description_text_view);
        characterImageView = (ImageView) testObject.getView().findViewById(R.id.character_image_view);
    }

    @Test
    public void nameAndDescriptionSetFromService() throws Exception {
        final String expectedDescription = "THERE IS ONLY ZUUL";
        final String expectedName = "ZUUL";

        responseObservable.onNext(buildCharacter(expectedDescription, expectedName,
                "gif", "expectedPath"));

        assertEquals(expectedName, nameTextView.getText().toString());
        assertEquals(expectedDescription, descriptionTextView.getText().toString());
    }

    @Test
    public void imageLoadedIntoView() throws Exception {
        final String extension = "gif";
        final String expectedPath = "expectedPath";

        responseObservable.onNext(buildCharacter("description", "name",
                extension, expectedPath));

        verify(imageLoader).loadImage(expectedPath + "." + extension, characterImageView);
    }

    @Test
    public void unexpectedFailureShowsToast() throws Exception {
        responseObservable.onError(new Throwable());

        assertEquals("Unexpected Error", ShadowToast.getTextOfLatestToast());
    }

    @NonNull
    private ResponseWrapper<Character> buildCharacter(final String expectedDescription,
                                                      final String expectedName,
                                                      final String extension,
                                                      final String expectedPath) {
        final List<Character> characterList = Collections.singletonList(new Character(123L,
                expectedName, expectedDescription, expectedPath, extension));
        final ListWrapper<Character> characterListWrapper = new ListWrapper<>();
        characterListWrapper.setResults(characterList);
        final ResponseWrapper<Character> results = new ResponseWrapper<>();
        results.setData(characterListWrapper);
        return results;
    }
}
