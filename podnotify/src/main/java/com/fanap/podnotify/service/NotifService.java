package com.fanap.podnotify.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fanap.podasync.Async;
import com.fanap.podnotify.util.PodServiceUtils;


/**
 * Created by Android Developer 1 on 11/21/2017.
 */


public class NotifService extends Service {

    private Async async;

    public NotifService() {
    }


    @Override
    public void onCreate() {

        async = Async.getInstance(getApplicationContext());

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PodServiceUtils.startService(getApplicationContext(),async);

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        PodServiceUtils.stopService(async);
    }

}
