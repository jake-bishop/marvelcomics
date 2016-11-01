package com.geekmode.marvelcomics.view;

import android.util.Log;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.Character;
import com.geekmode.marvelcomics.model.ResponseWrapper;
import com.geekmode.marvelcomics.model.Thumbnail;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.services.ObservableDatabase;

import javax.inject.Inject;

public class CharacterPresenter extends Presenter<CharacterFragment> {

    private final CharacterService characterService;
    private final SchedulerProvider schedulerProvider;
    private ObservableDatabase observableDatabase;
    private String characterId;
    private Character character;

    @Inject
    public CharacterPresenter(final CharacterService characterService, final SchedulerProvider schedulerProvider, final ObservableDatabase observableDatabase) {
        this.characterService = characterService;
        this.schedulerProvider = schedulerProvider;
        this.observableDatabase = observableDatabase;
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

    private void handleResponse(final ResponseWrapper<Character> response) {
        if (!isViewAttached()) {
            return;
        }

        Character character = response.getData().getResults().get(0);

        getPresenterView().updateName(character.getName());
        getPresenterView().updateDescription(character.getDescription());
        getPresenterView().updateAttribution(response.getAttributionText());

        final Thumbnail thumbnail = character.getThumbnail();
        getPresenterView().updateImage(thumbnail.getPath() + "." + thumbnail.getExtension());

    }

    private void handleError(final Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage(), throwable);

        if (!isViewAttached()) {
            return;
        }

        getPresenterView().handleError(throwable);
    }
}
