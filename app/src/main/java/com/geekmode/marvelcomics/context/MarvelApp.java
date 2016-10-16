package com.geekmode.marvelcomics.context;

import com.geekmode.marvelcomics.injection.ApplicationComponent;
import com.geekmode.marvelcomics.injection.ApplicationModule;
import com.geekmode.marvelcomics.injection.DaggerApplicationComponent;
import com.geekmode.marvelcomics.injection.ServiceModule;

public class MarvelApp extends BaseApplication {
    ApplicationComponent applicationComponent;

    @Override
    public ApplicationComponent getApplicationComponent() {

        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .serviceModule(new ServiceModule())
                    .build();
        }

        return applicationComponent;
    }

}
