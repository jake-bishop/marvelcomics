package com.geekmode.marvelcomics;

import com.geekmode.marvelcomics.injection.ApplicationComponent;

public class TestMarvelApp extends MarvelApp {
    private ApplicationComponent testApplicationComponent;

    @Override
    public ApplicationComponent getApplicationComponent() {
        return testApplicationComponent;
    }

    public void setComponent(ApplicationComponent testApplicationComponent){
        this.testApplicationComponent = testApplicationComponent;
    }
}
