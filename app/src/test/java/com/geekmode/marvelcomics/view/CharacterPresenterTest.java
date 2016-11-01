package com.geekmode.marvelcomics.view;

import android.support.annotation.NonNull;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.Character;
import com.geekmode.marvelcomics.model.ListWrapper;
import com.geekmode.marvelcomics.model.ResponseWrapper;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.services.ObservableDatabase;

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
    @Mock
    ObservableDatabase mockObservableDatabase;

    private CharacterPresenter testObject;

    private String expectedComicId = "123";
    private String expectedCharacterId = "456";

    @Before
    public void setUp() throws Exception {
        when(mockService.characterById(expectedCharacterId)).thenReturn(Observable.empty());
        when(schedulerProvider.getIoScheduler()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.getMainScheduler()).thenReturn(Schedulers.immediate());

        testObject = new CharacterPresenter(mockService, schedulerProvider, mockObservableDatabase);
        testObject.setCharacterId(expectedCharacterId);
    }

    @Test
    public void refreshData_noInteractionsIfNoView() throws Exception {
        when(mockService.characterById(anyString())).thenReturn(Observable.just(getCharactersResponse("ZUUL")));

        testObject.refreshCharacterData();

        Mockito.verifyZeroInteractions(mockPresenterView);
    }

    @Test
    public void refreshData() throws Exception {
        final String expectedName = "ZUUL";
        when(mockService.characterById(anyString())).thenReturn(Observable.just(getCharactersResponse(expectedName)));

        testObject.attachView(mockPresenterView);
        testObject.refreshCharacterData();

        Mockito.verify(mockPresenterView).updateName(expectedName);
    }

    @NonNull
    private ResponseWrapper<Character> getCharactersResponse(String expectedName) {
        final ResponseWrapper<Character> responseWrapper = new ResponseWrapper<>();
        final ListWrapper<Character> listWrapper = new ListWrapper<>();
        final Character character = new Character(123L, expectedName, "THERE IS ONLY ZUUL", "something", "other");

        listWrapper.setResults(Collections.singletonList(character));
        responseWrapper.setData(listWrapper);

        return responseWrapper;
    }
}