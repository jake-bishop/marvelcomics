package com.geekmode.marvelcomics.view;

import android.support.annotation.NonNull;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.Character;
import com.geekmode.marvelcomics.model.ListWrapper;
import com.geekmode.marvelcomics.model.ResponseWrapper;
import com.geekmode.marvelcomics.services.CharacterService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    CharacterService mockService;
    @Mock
    MainActivity mockPresenterView;
    @Mock
    SchedulerProvider schedulerProvider;

    private MainPresenter testObject;

    private String expectedComicId = "123";

    @Before
    public void setUp() throws Exception {
        when(mockService.charactersOfComic(expectedComicId)).thenReturn(Observable.empty());
        when(schedulerProvider.getIoScheduler()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.getMainScheduler()).thenReturn(Schedulers.immediate());

        testObject = new MainPresenter(mockService, schedulerProvider);
    }

    @Test
    public void refreshData_noInteractionsIfNoView() throws Exception {
        final String expectedName = "MY NAME";
        final ResponseWrapper<Character> characters = buildCharacter("", expectedName, "", "");
        when(mockService.charactersOfComic(expectedComicId)).thenReturn(Observable.just(characters));

        testObject.refreshCharacters(expectedComicId);

        Mockito.verifyZeroInteractions(mockPresenterView);
    }

    @Test
    public void refreshData() throws Exception {
        final String expectedName = "MY NAME";
        final ResponseWrapper<Character> characters = buildCharacter("", expectedName, "", "");
        when(mockService.charactersOfComic(expectedComicId)).thenReturn(Observable.just(characters));

        testObject.attachView(mockPresenterView);
        testObject.refreshCharacters(expectedComicId);

        Mockito.verify(mockPresenterView).displayCharacters(characters.getData().getResults());
    }

    @NonNull
    private ResponseWrapper<Character> buildCharacter(final String expectedDescription, final String expectedName, final String extension, final String expectedPath) {
        List<Character> characterList = new ArrayList<>(Collections.singletonList(
                new Character(123L, expectedName, expectedDescription, expectedPath, extension)));
        final ListWrapper<Character> characterListWrapper = new ListWrapper<>();
        characterListWrapper.setResults(characterList);
        final ResponseWrapper<Character> characters = new ResponseWrapper<>();
        characters.setData(characterListWrapper);
        return characters;
    }
}