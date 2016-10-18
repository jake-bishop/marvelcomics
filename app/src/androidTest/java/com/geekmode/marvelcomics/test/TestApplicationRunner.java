package com.geekmode.marvelcomics.test;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import com.geekmode.marvelcomics.context.TestApplication;

public class TestApplicationRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(final ClassLoader cl, final String className, final Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestApplication.class.getName(), context);
    }
}
