package com.geekmode.marvelcomics.injection;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulerProvider {

    public Scheduler getIoScheduler() {
        return Schedulers.io();
    }

    public Scheduler getMainScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
