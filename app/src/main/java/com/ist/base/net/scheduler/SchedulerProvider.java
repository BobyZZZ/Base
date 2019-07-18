package com.ist.base.net.scheduler;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProvider implements BaseSchedulerProvider {

    private static SchedulerProvider instance;

    private SchedulerProvider() {

    }

    public static SchedulerProvider getInstance() {
        if (instance == null) {
            synchronized (SchedulerProvider.class) {
                if (instance == null) {
                    instance = new SchedulerProvider();
                }
            }
        }
        return instance;
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(io())
                .observeOn(ui());
    }
}
