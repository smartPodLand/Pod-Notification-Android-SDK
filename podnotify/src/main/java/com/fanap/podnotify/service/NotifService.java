package com.fanap.podnotify.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fanap.podasync.Async;
import com.fanap.podnotify.util.PodServiceUtils;



/**
 * Created by arvin
 * on Mon, 17 December 2018 at 12:45 PM.
 * hi [at] arvinrokni [dot] ir
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                PodServiceUtils.startService(getApplicationContext(),async);
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PodServiceUtils.stopService(async);
            }
        }).start();

    }

}
