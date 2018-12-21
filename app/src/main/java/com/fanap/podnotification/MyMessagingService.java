package com.fanap.podnotification;

import android.util.Log;

import com.fanap.podnotify.model.Notification;
import com.fanap.podnotify.service.PodMessagingService;

public class MyMessagingService extends PodMessagingService {

    @Override
    public void onMessageReceived(Notification notification) {
        Log.i("MESSAGE TEXT: ",notification.getText());
        super.onMessageReceived(notification);
    }

}
