package com.geekmode.marvelcomics;

import android.support.annotation.NonNull;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.CharacterData;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.model.Thumbnail;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.view.CharacterFragment;
import com.geekmode.marvelcomics.view.CharacterPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterPresenterTest {

    @Mock
    CharacterService mockService;
    @Mock
    SchedulerProvider schedulerProvider;
    @Mock
    CharacterFragment mockPresenterView;

    private CharacterPresenter testObject;

    private String expectedComicId = "123";
    private String expectedCharacterId = "456";

    @Before
    public void setUp() throws Exception {
        when(mockService.characterById(expectedComicId)).thenReturn(Observable.empty());
        when(schedulerProvider.getIoScheduler()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.getMainScheduler()).thenReturn(Schedulers.immediate());

        testObject = new CharacterPresenter(mockService, schedulerProvider);
//        testObject.setCharacterId(expectedCharacterId);
    }

    @Test
    public void refreshData_noInteractionsIfNoView() throws Exception {
        final String expectedName = "MY NAME";

        final CharactersResponse charactersResponse = buildCharacter("", expectedName, "", "");
        when(mockService.characterById(anyString())).thenReturn(Observable.just(charactersResponse));

//        testObject.refreshCharacterData();

        Mockito.verifyZeroInteractions(mockPresenterView);
    }

    @Test
    public void refreshData() throws Exception {
        final String expectedName = "MY NAME";

        final CharactersResponse charactersResponse = buildCharacter("", expectedName, "", "");
        when(mockService.characterById(anyString())).thenReturn(Observable.just(charactersResponse));

        testObject.attachView(mockPresenterView);
//        testObject.refreshCharacterData();
//        Mockito.verify(mockPresenterView).updateName(expectedName);
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