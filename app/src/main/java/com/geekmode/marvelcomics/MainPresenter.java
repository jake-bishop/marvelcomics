package com.geekmode.marvelcomics;

import android.util.Log;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.view.Presenter;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainActivity> {
    private final CharacterService characterService;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public MainPresenter(final CharacterService characterService, final SchedulerProvider schedulerProvider) {
        this.characterService = characterService;
        this.schedulerProvider = schedulerProvider;
    }

    public void refreshCharacters(String issueId) {
        characterService.charactersOfComic(issueId)
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(final CharactersResponse charactersResponse) {
        if (!isViewAttached()) {
            return;
        }

        getPresenterView().displayCharacters(charactersResponse.getData().getResults());
    }

    private void handleError(final Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage(), throwable);

        if (!isViewAttached()) {
            return;
        }

        getPresenterView().handleError(throwable);
    }

}