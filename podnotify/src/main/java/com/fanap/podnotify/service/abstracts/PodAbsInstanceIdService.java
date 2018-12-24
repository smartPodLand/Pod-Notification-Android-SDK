package com.fanap.podnotify.service.abstracts;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.fanap.podnotify.model.ExtraConst.Constants;

/**
 * Created by arvin
 * on Mon, 17 December 2018 at 12:45 PM.
 * hi [at] arvinrokni [dot] ir
 */


public abstract class PodAbsInstanceIdService extends Service {

    private static MyBinder myBinder = new MyBinder();

    public abstract void onPeerIdChanged(String peerId);

    @Override
    public final IBinder onBind(Intent intent) {

        if (intent.getStringExtra(Constants.PEER_ID_DATA_KEY) != null &&
                !intent.getStringExtra(Constants.PEER_ID_DATA_KEY).isEmpty()){
            String pid = intent.getStringExtra(Constants.PEER_ID_DATA_KEY);
            onPeerIdChanged(pid);
        }

        return myBinder;
    }

    public static class MyBinder extends Binder {
        public PodAbsInstanceIdService getService() {
            return getService();
        }
    }
}
