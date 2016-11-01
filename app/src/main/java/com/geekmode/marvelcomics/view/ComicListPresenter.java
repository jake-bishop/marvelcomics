package com.geekmode.marvelcomics.view;

import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.Comic;
import com.geekmode.marvelcomics.model.ComicData;
import com.geekmode.marvelcomics.services.CharacterService;

public class ComicListPresenter extends Presenter<ComicListActivity> {

    private final CharacterService marvelComicsService;
    private final SchedulerProvider schedulerProvider;
    private final int PAGE_SIZE = 20;
    private final String CHARACTER_ID = "1009718";
    boolean loading = false;
    private int offset = 0;
    private int total = 0;

    public ComicListPresenter(final CharacterService characterService, final SchedulerProvider schedulerProvider) {
        this.marvelComicsService = characterService;
        this.schedulerProvider = schedulerProvider;
    }

    void refreshCharacters() {
        System.out.println("PAGER - offset: " + offset);
        marvelComicsService.comics(CHARACTER_ID, offset, PAGE_SIZE)
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(this::handleResponse, this::handleError);
    }

    void getMoreCharacters() {
        if (!loading) {
            loading = true;
            offset = Math.min(offset + PAGE_SIZE, total);
            refreshCharacters();
            loading = false;
        }
    }

    private void handleError(Throwable throwable) {
        getPresenterView().showError();
    }

    private void handleResponse(Comic comic) {
        if ("Ok".equalsIgnoreCase(comic.getStatus())) {
            ComicData comicData = comic.getData();
            total = comicData.getTotal();
            System.out.println("PAGER - count=" + comicData.getCount() + " total=" + comicData.getTotal());
            if (comicData.getCount() > 0) {
                getPresenterView().showComics(comicData.getResults());
            }
        }
    }

    public void comicClicked(long comicId) {
        getPresenterView().startComicCardsActivity(comicId);
    }
}
