package com.geekmode.marvelcomics.context;

import com.geekmode.marvelcomics.injection.ApplicationComponent;

public class TestApplication extends BaseApplication {
    private ApplicationComponent testApplicationComponent;

    public void setApplicationComponent(final ApplicationComponent applicationComponent) {
        this.testApplicationComponent = applicationComponent;
    }

    @Override
    public ApplicationComponent getApplicationComponent() {
        return testApplicationComponent;
    }
}
