package com.github.rxlocation;

import android.app.Application;

import rxlocation.github.pitt.RxLocation;

/**
 * Created by pitt on 2017/3/17.
 */

public class RxLocationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxLocation.initialize(this);
    }

}
