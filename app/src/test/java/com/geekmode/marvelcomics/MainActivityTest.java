package com.geekmode.marvelcomics;

import android.widget.ImageView;
import android.widget.TextView;

import com.geekmode.marvelcomics.injection.ApplicationComponent;
import com.geekmode.marvelcomics.injection.ApplicationModule;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.CharacterData;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.model.Thumbnail;
import com.geekmode.marvelcomics.services.CharacterService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.Date;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import rx.Observable;
import rx.schedulers.Schedulers;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP, application = TestMarvelApp.class)
public class MainActivityTest {
    @Rule
    public DaggerMockRule<ApplicationComponent> daggerMockRule =
            new DaggerMockRule<>(ApplicationComponent.class,
                    new ApplicationModule(((TestMarvelApp) RuntimeEnvironment.application)))
                    .set(applicationComponent ->
                            ((TestMarvelApp) RuntimeEnvironment.application)
                                    .setComponent(applicationComponent));
    @Mock
    private CharacterService characterService;
    @Mock
    private ImageUtil imageUtil;
    @Mock
    private SchedulerProvider schedulerProvider;

    private MainActivity testObject;
    private String expectedName;
    private String expectedDescription;

    @Before
    public void setup() {
        expectedName = "Gambit";
        expectedDescription = "Gambit absorbs energy to charge objects that he can propel, such as playing cards.";
        CharactersResponse charactersResponse = buildCharacterResponse(expectedName, expectedDescription,
                "Copyleft 2016 Acme, Inc.", "somepath");

        Observable<CharactersResponse> observable = Observable.just(charactersResponse);

        when(characterService.characters(anyString())).thenReturn(observable);

        when(schedulerProvider.getMainScheduler()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.getIoScheduler()).thenReturn(Schedulers.immediate());

        testObject = Robolectric.buildActivity(MainActivity.class).setup().get();
    }

    @Test
    public void testCharacterFieldsSetFromCharacterService() throws Exception {
        TextView nameTextView = (TextView) testObject.findViewById(R.id.title_text_view);
        TextView descriptionTextView = (TextView) testObject.findViewById(R.id.description_text_view);

        assertEquals(expectedName, nameTextView.getText().toString());
        assertEquals(expectedDescription, descriptionTextView.getText().toString());
    }

    @Test
    public void testImageLoader() throws Exception {
        ImageView imageView = (ImageView) testObject.findViewById(R.id.character_image_view);

        verify(imageUtil).loadImage("somepath.jpg", imageView);
    }

    @After
    public void tearDown() {
        testObject.onStop();
        testObject.onDestroy();
    }

    private CharactersResponse buildCharacterResponse(String name, String description, String attribution, String path) {
        CharactersResponse retval = new CharactersResponse();
        retval.setAttributionText(attribution);
        retval.setCode(200);
        retval.setStatus("Ok");
        retval.setEtag("abc123yadayadayada");

        CharacterData characterData = new CharacterData();
        characterData.setLimit(0);
        characterData.setCount(1);
        characterData.setOffset(0);
        characterData.setTotal(1);

        CharacterModel characterModel = new CharacterModel();
        characterModel.setName(name);
        characterModel.setDescription(description);
        characterModel.setId(1);
        characterModel.setModified(new Date());

        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setPath(path); // "http://i.annihil.us/u/prod/marvel/i/mg/a/40/52696aa8aee99"
        thumbnail.setExtension("jpg");
        characterModel.setThumbnail(thumbnail);

        characterData.setResults(Collections.singletonList(characterModel));

        retval.setData(characterData);

        return retval;
    }
}
