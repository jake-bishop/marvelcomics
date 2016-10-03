package com.geekmode.marvelcomics;

import android.widget.ImageView;
import android.widget.TextView;

import com.geekmode.marvelcomics.dagger.ApplicationComponent;
import com.geekmode.marvelcomics.dagger.ApplicationModule;
import com.geekmode.marvelcomics.images.ImageUtil;
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
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Date;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import rx.Observable;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP, application = TestMarvelApp.class)
public class MainActivityTest {
    @Mock
    private CharacterService characterService;
    @Mock
    private ImageUtil imageUtil;

    MainActivity testObject;

    @Rule
    public DaggerMockRule<ApplicationComponent> daggerMockRule =
            new DaggerMockRule<>(ApplicationComponent.class,
                    new ApplicationModule(((TestMarvelApp) RuntimeEnvironment.application)))
                    .set(applicationComponent ->
                            ((TestMarvelApp) RuntimeEnvironment.application)
                                    .setComponent(applicationComponent));
    private String expectedPath;
    private String expectedName;
    private String expectedDescription;
    private String expectedAttribution;
    private CharactersResponse charactersResponse;
    private Observable<CharactersResponse> observable;

    @Before
    public void setup() {
        expectedName = "Gambit";
        expectedDescription = "Gambit absorbs energy to charge objects that he can propel, such as playing cards.";
        expectedAttribution = "Copyleft 2016 Acme, Inc.";
        expectedPath = "somepath";
        charactersResponse = buildCharacterResponse(expectedName, expectedDescription, expectedAttribution, expectedPath);

        observable = Observable.just(charactersResponse);

        when(characterService.characters(anyString())).thenReturn(observable);
    }

    @Test
    public void testCharacterFieldsSetFromCharacterService() throws Exception {
        testObject = Robolectric.buildActivity(MainActivity.class).setup().get();

        TextView nameTextView = (TextView) testObject.findViewById(R.id.title_text_view);
        TextView descriptionTextView = (TextView) testObject.findViewById(R.id.description_text_view);

        assertEquals(expectedName, nameTextView.getText().toString());
        assertEquals(expectedDescription, descriptionTextView.getText().toString());
    }

    @Test
    public void testImageLoader() throws Exception {
        testObject = Robolectric.buildActivity(MainActivity.class).setup().get();

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

        characterData.setResults(Arrays.asList(characterModel));

        retval.setData(characterData);

        return retval;
    }
}
