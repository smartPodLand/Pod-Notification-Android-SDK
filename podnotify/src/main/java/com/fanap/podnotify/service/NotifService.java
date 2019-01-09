package com.fanap.podnotify.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fanap.podasync.Async;
import com.fanap.podnotify.PodNotificationListener;
import com.fanap.podnotify.model.ExtraConst;
import com.fanap.podnotify.util.PodServiceUtils;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
*/

public class NotifService extends Service {

    private Async async;
    private PodServiceUtils serviceUtils;

    public NotifService() {
    }

    @Override
    public void onCreate() {

        async = Async.getInstance(getApplicationContext());
        PodNotificationListener listener = new PodNotificationListener(NotifService.this);
        serviceUtils = new PodServiceUtils(async, listener);
        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceUtils.startService(getApplicationContext());
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent,flags,startId);
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
                serviceUtils.stopService();
            }
        }).start();

        Intent broadcastIntent = new Intent(ExtraConst.Constants.SERVICE_RESTART_EVENT);
        broadcastIntent.setPackage(getApplicationContext().getPackageName());
        sendBroadcast(broadcastIntent);

    }

}
