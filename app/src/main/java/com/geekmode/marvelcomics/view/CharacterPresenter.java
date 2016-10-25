package com.geekmode.marvelcomics.view;

import android.util.Log;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.services.CharacterService;

import javax.inject.Inject;

public class CharacterPresenter extends Presenter<CharacterFragment> {

    private final CharacterService characterService;
    private final SchedulerProvider schedulerProvider;
    private String characterId;

    @Inject
    public CharacterPresenter(final CharacterService characterService, final SchedulerProvider schedulerProvider) {
        this.characterService = characterService;
        this.schedulerProvider = schedulerProvider;
    }

    void setCharacterId(final String characterId) {
        this.characterId = characterId;
    }

    void refreshCharacterData() {
        if (characterId == null) {
            return;
        }

        characterService.characterById(characterId)
                           .subscribeOn(schedulerProvider.getIoScheduler())
                           .observeOn(schedulerProvider.getMainScheduler())
                           .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(final CharactersResponse charactersResponse) {
        if (!isViewAttached()) {
            return;
        }

        final CharacterModel firstCharacter = charactersResponse.getFirstCharacter();
        getPresenterView().updateName(firstCharacter.getName());
        getPresenterView().updateDescription(firstCharacter.getDescription());
        getPresenterView().updateAttribution(charactersResponse.getAttributionText());

        getPresenterView().updateImage(firstCharacter.getThumbnailPath());
    }

    private void handleError(final Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage(), throwable);

        if (!isViewAttached()) {
            return;
        }

        getPresenterView().handleError(throwable);
    }
}
