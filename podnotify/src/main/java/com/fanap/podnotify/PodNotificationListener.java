package com.fanap.podnotify;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.fanap.podasync.Async;
import com.fanap.podasync.AsyncListener;
import com.fanap.podnotify.model.AsyncConst;
import com.fanap.podnotify.util.PodServiceUtils;
import com.fanap.podnotify.util.SharedPref;

import java.io.IOException;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
*/


public class PodNotificationListener implements AsyncListener {

    private static final String TAG = PodNotificationListener.class.getSimpleName();

    private Context context;
    private ServiceConnection connection;

    public PodNotificationListener(Context context) {
        this.context = context;
        this.connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //not used
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                //not used
            }
        };
    }

    @Override
    public void onReceivedMessage(String textMessage){
        PodServiceUtils.callOnMessageReceived(context, connection, textMessage);
        Log.i(TAG,"message: " + textMessage);
    }

    @Override
    public void onStateChanged(String state) {
        Log.i(TAG,state);

        SharedPref.getInstance(context).edit().putString("NOTIFICATION_STATE_TO_USE",state).apply();

        if (state.equals(AsyncConst.Constants.ASYNC_READY)){
            String pid = Async.getInstance(context).getPeerId();
            PodServiceUtils.callOnPeerIdChanged(context,connection,pid);
            SharedPreferences sharedPreferences = SharedPref.getInstance(context);
            String handShakeNeededKey = PodNotify.getAppId(context.getApplicationContext()) + "handshake";
            if (!sharedPreferences.getString(handShakeNeededKey, "").equals(pid)) {
                sharedPreferences.edit().putString(handShakeNeededKey, pid).apply();
                PodServiceUtils.doFirstTimeHandShake(context);
            }
        }
    }

    @Override
    public void onDisconnected(String textMessage) throws IOException {
        Log.i(TAG , "Disconnected");
    }

    @Override
    public void onError(String textMessage) throws IOException {
        Log.wtf(TAG + ": Error", textMessage);
    }

    @Override
    public void handleCallbackError(Throwable cause) throws Exception {
        Log.w(TAG + ": handleCallbackError", cause.getMessage());
    }
}
