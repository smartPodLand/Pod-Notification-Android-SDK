package com.fanap.podnotify.service.abstracts;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.fanap.podasync.util.JsonUtil;
import com.fanap.podnotify.model.ExtraConst.Constants;
import com.fanap.podnotify.model.Notification;

import org.json.JSONException;

public abstract class PodAbsMessagingService extends Service  {

    private static MyBinder myBinder = new MyBinder();

    public abstract void onMessageReceived(Notification notification);

    @Override
    public final IBinder onBind(Intent intent) {

        if (intent.getStringExtra(Constants.MESSAGE_DATA_KEY) != null &&
                !intent.getStringExtra(Constants.MESSAGE_DATA_KEY).isEmpty()){
            String stringExtra = intent.getStringExtra(Constants.MESSAGE_DATA_KEY);
            try {
                String content = JsonUtil.getJsonObject(stringExtra).getString("content");
                Notification notification = JsonUtil.fromJSON(content, Notification.class);
                onMessageReceived(notification);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return myBinder;
    }

    public static class MyBinder extends Binder {
        PodAbsMessagingService getService() {
            return getService();
        }
    }
}
