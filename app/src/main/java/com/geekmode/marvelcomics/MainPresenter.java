package com.geekmode.marvelcomics;

import android.util.Log;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.view.Presenter;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainActivity> {
    private final CharacterService characterService;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public MainPresenter(final CharacterService characterService, SchedulerProvider schedulerProvider) {
        this.characterService = characterService;
        this.schedulerProvider = schedulerProvider;
    }

    void refreshCharacterData() {
        characterService.characters("Thor")
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(final CharactersResponse charactersResponse) {
        if (!isViewAttached()) {
            return;
        }

        final CharacterModel firstCharacter = charactersResponse.getFirstCharacter();

        if (firstCharacter != null) {
            String title = firstCharacter.getName();
            String description = firstCharacter.getDescription();
            String attribution = charactersResponse.getAttributionText();

            getPresenterView().updateName(title);
            getPresenterView().updateAttribution(attribution);
            getPresenterView().updateImage(firstCharacter.getThumbnailPath());

            if (description != null && !description.isEmpty()) {
                getPresenterView().updateDescription(description);
            } else {
                getPresenterView().updateDescription(R.string.character_description);
            }
        }
    }

    private void handleError(final Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage(), throwable);

        if (!isViewAttached()) {
            return;
        }

        getPresenterView().handleError(throwable);
    }
}