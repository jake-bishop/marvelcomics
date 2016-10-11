package com.geekmode.marvelcomics.context;

import android.app.Application;

import com.geekmode.marvelcomics.injection.ApplicationComponent;

public abstract class BaseApplication extends Application {

    public abstract ApplicationComponent getApplicationComponent();
}