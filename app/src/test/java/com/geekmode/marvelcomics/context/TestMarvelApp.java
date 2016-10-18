package com.geekmode.marvelcomics.context;

import com.geekmode.marvelcomics.injection.ApplicationComponent;

public class TestMarvelApp extends BaseApplication {
    private ApplicationComponent testApplicationComponent;

    @Override
    public ApplicationComponent getApplicationComponent() {
        return testApplicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent testApplicationComponent){
        this.testApplicationComponent = testApplicationComponent;
    }
}
