package com.fanap.podnotification;

import android.app.Application;

import com.fanap.podnotify.PodNotify;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PodNotify.setApplication(this);

    }


}
