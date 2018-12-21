package com.fanap.podnotify;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.fanap.podasync.Async;
import com.fanap.podasync.AsyncListener;
import com.fanap.podnotify.util.PodServiceUtils;
import com.fanap.podnotify.util.SharedPref;

import java.io.IOException;

public class PodNotificationListener implements AsyncListener {

    private static final String TAG = PodNotificationListener.class.getSimpleName();

    private Context context;
    private ServiceConnection connection;

    public PodNotificationListener(Context context) {
        this.context = context;
        this.connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    @Override
    public void onReceivedMessage(String textMessage) throws IOException {
        Log.i(TAG,"message: " + textMessage);
        PodServiceUtils.callOnMessageReceived(context, connection, textMessage);
    }

    @Override
    public void onStateChanged(String state) throws IOException {
        Log.i(TAG,state);
        if (state.equals("ASYNC_READY")){
            String pid = Async.getInstance(context).getPeerId();
            PodServiceUtils.callOnPeerIdChanged(context,connection,pid);
            SharedPreferences sharedPreferences = SharedPref.getInstance(context);
            String handShakeNeededKey = PodNotify.getAppId() + "handshaked";
            if (sharedPreferences.getBoolean(handShakeNeededKey, true)) {
                sharedPreferences.edit().putBoolean(handShakeNeededKey, false).apply();
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
