package com.geekmode.marvelcomics;

import android.support.annotation.NonNull;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.CharacterData;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.model.Thumbnail;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.view.MainActivity;
import com.geekmode.marvelcomics.view.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

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
        final CharactersResponse charactersResponse = buildCharacter("", expectedName, "", "");
        when(mockService.charactersOfComic(expectedComicId)).thenReturn(Observable.just(charactersResponse));

        testObject.refreshCharacters(expectedComicId);

        Mockito.verifyZeroInteractions(mockPresenterView);
    }

    @Test
    public void refreshData() throws Exception {
        final String expectedName = "MY NAME";
        final CharactersResponse charactersResponse = buildCharacter("", expectedName, "", "");
        when(mockService.charactersOfComic(expectedComicId)).thenReturn(Observable.just(charactersResponse));

        testObject.attachView(mockPresenterView);
        testObject.refreshCharacters(expectedComicId);

//        Mockito.verify(mockPresenterView).displayCharacters(charactersResponse.getData().getResults());
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