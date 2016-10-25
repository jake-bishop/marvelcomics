package com.geekmode.marvelcomics.view;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.Comic;
import com.geekmode.marvelcomics.services.CharacterService;

import javax.inject.Inject;

public class ComicListPresenter extends Presenter<ComicListActivity> {

    private final CharacterService marvelComicsService;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ComicListPresenter(final CharacterService characterService, final SchedulerProvider schedulerProvider) {
        this.marvelComicsService = characterService;
        this.schedulerProvider = schedulerProvider;
    }

    void refreshCharacters() {
        marvelComicsService.comics("1009718")
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleError(Throwable throwable) {
        getPresenterView().showError();
    }

    private void handleResponse(Comic comic) {
        getPresenterView().showComics(comic.getData().getResults());
    }

}
