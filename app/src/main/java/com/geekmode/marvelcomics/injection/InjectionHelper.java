package com.geekmode.marvelcomics.injection;

import android.content.Context;

import com.geekmode.marvelcomics.context.BaseApplication;

public final class InjectionHelper {

    private InjectionHelper() {
    }

    public static ApplicationComponent getApplicationComponent(final Context context) {
        return ((BaseApplication) context.getApplicationContext()).getApplicationComponent();
    }

}
