package com.fanap.podnotify.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fanap.podnotify.model.Content;
import com.fanap.podnotify.model.ExtraConst;
import com.fanap.podnotify.util.PodServiceUtils;

public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getStringExtra(ExtraConst.Constants.NOTIFICATION_ACTION);
        String id = intent.getStringExtra(ExtraConst.Constants.NOTIFICATION_ID);
        String senderId = intent.getStringExtra(ExtraConst.Constants.NOTIFICATION_SENDER_ID);

        if (action.equals(ExtraConst.Constants.NOTIFICATION_ACTION_OPEN)){
            PodServiceUtils.handShake(context.getApplicationContext(), id, senderId, Content.Type.OPEN);
        } else if (action.equals(ExtraConst.Constants.NOTIFICATION_ACTION_DISMISS)){
            PodServiceUtils.handShake(context.getApplicationContext(), id, senderId, Content.Type.DISMISS);
        }
    }
}
