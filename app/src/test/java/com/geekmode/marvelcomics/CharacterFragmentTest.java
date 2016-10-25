package com.geekmode.marvelcomics;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekmode.marvelcomics.context.TestMarvelApp;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.ApplicationComponent;
import com.geekmode.marvelcomics.injection.ApplicationModule;
import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.injection.ServiceModule;
import com.geekmode.marvelcomics.model.CharacterData;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.model.Thumbnail;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.view.CharacterFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.Collections;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP, application = TestMarvelApp.class)
public class CharacterFragmentTest {

    @Mock
    private CharacterService characterService;
    @Mock
    private ImageUtil imageLoader;
    @Mock
    private SchedulerProvider schedulerProvider;

    private final PublishSubject<CharactersResponse> responseObservable = PublishSubject.create();

    @Rule
    public DaggerMockRule<ApplicationComponent> daggerMockRule = new DaggerMockRule<>(
            ApplicationComponent.class,
            new ApplicationModule(((TestMarvelApp) RuntimeEnvironment.application)),
            new ServiceModule())
            .set(applicationComponent -> ((TestMarvelApp) RuntimeEnvironment.application).setApplicationComponent(applicationComponent));

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
        final String extension = ".gif";
        final String expectedPath = "expectedPath";
        final CharactersResponse charactersResponse = buildCharacter(expectedDescription, expectedName, extension, expectedPath);

        final List<CharactersResponse> results = Collections.singletonList(charactersResponse);

        responseObservable.onNext(charactersResponse);

        assertEquals(expectedName, nameTextView.getText().toString());
        assertEquals(expectedDescription, descriptionTextView.getText().toString());
    }

    @Test
    public void imageLoadedIntoView() throws Exception {
        final String extension = ".gif";
        final String expectedPath = "expectedPath";
        final CharactersResponse charactersResponse = buildCharacter("description", "name", extension, expectedPath);

        final List<CharactersResponse> results = Collections.singletonList(charactersResponse);

        responseObservable.onNext(charactersResponse);

        verify(imageLoader).loadImage(expectedPath + "." + extension, characterImageView);
    }

    @Test
    public void unexpectedFailureShowsToast() throws Exception {
        responseObservable.onError(new Throwable());

        assertEquals("Unexpected Error", ShadowToast.getTextOfLatestToast());
    }

    @NonNull
    private CharactersResponse buildCharacter(final String expectedDescription, final String expectedName, final String extension, final String expectedPath) {
        final CharactersResponse charactersResponse = new CharactersResponse();
        CharacterData data = new CharacterData();
        CharacterModel model = new CharacterModel();
        data.setResults(Collections.singletonList(model));
        model.setDescription(expectedDescription);
        model.setName(expectedName);
        final Thumbnail thumbnail = new Thumbnail();
        thumbnail.setExtension(extension);
        thumbnail.setPath(expectedPath);
        model.setThumbnail(thumbnail);
        charactersResponse.setData(data);
        return charactersResponse;
    }
}
