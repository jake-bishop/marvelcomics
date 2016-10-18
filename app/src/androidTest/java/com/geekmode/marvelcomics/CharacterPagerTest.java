package com.geekmode.marvelcomics;


import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.geekmode.marvelcomics.context.TestApplication;
import com.geekmode.marvelcomics.injection.ApplicationComponent;
import com.geekmode.marvelcomics.injection.ApplicationModule;
import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.injection.ServiceModule;
import com.geekmode.marvelcomics.model.CharacterData;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.model.Thumbnail;
import com.geekmode.marvelcomics.services.CharacterService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CharacterPagerTest {

    @Rule
    public DaggerMockRule<ApplicationComponent> daggerMockRule = new DaggerMockRule<>(
            ApplicationComponent.class,
            new ApplicationModule(((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext())),
            new ServiceModule())
            .set(applicationComponent -> getApplicationContext().setApplicationComponent(applicationComponent));

    private static TestApplication getApplicationContext() {
        return (TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Mock
    CharacterService mockService;
    @Mock
    SchedulerProvider schedulerProvider;

    @Before
    public void setUp() throws Exception {
        when(mockService.charactersOfComic(anyString())).thenReturn(Observable.empty());
        when(mockService.characterById(anyString())).thenReturn(Observable.empty());
        when(schedulerProvider.getIoScheduler()).thenReturn(AndroidSchedulers.mainThread());
        when(schedulerProvider.getMainScheduler()).thenReturn(AndroidSchedulers.mainThread());
    }


    @Test
    public void displayTwoPages() throws Exception {
        final String expectedName1 = "ZUUL_1";
        final String expectedName2 = "ZUUL_2";

        when(mockService.charactersOfComic(anyString())).thenReturn(Observable.just(getCharactersResponse(1, 2)));
        when(mockService.characterById(String.valueOf(1))).thenReturn(Observable.just(getCharactersResponse(1)));
        when(mockService.characterById(String.valueOf(2))).thenReturn(Observable.just(getCharactersResponse(2)));

        activityTestRule.launchActivity(null);

        onView(withText(expectedName1)).check(matches(isDisplayed()));
        onView(withText(expectedName2)).check(matches(not(isDisplayed())));

        onView(withId(R.id.character_pager)).perform(swipeLeft());
        onView(withText(expectedName2)).check(matches(isDisplayed()));
    }

    @NonNull
    private CharactersResponse getCharactersResponse(final long... expectedIds) {
        final CharactersResponse responses = new CharactersResponse();
        final CharacterData data = new CharacterData();
        List<CharacterModel> results = new ArrayList<>();
        for (final long expectedId : expectedIds) {
            final CharacterModel model = buildCharacterModel(expectedId, "There is only Zuul!", "ZUUL_" + expectedId, "jpg", "zuul");
            results.add(model);
        }
        data.setResults(results);
        responses.setData(data);
        return responses;
    }

    @NonNull
    private CharacterModel buildCharacterModel(final long characterId, final String expectedDescription, final String expectedName, final String extension, final String expectedPath) {
        final CharacterModel model = new CharacterModel();
        model.setDescription(expectedDescription);
        model.setName(expectedName);
        model.setId(characterId);
        final Thumbnail thumbnail = new Thumbnail();
        thumbnail.setExtension(extension);
        thumbnail.setPath(expectedPath);
        model.setThumbnail(thumbnail);
        return model;
    }
}
