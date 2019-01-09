package com.fanap.podnotify.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ArvinRokni
 * on Sun, 09 December 2018 at 5:57 PM.
*/
public abstract class ExtraConst {

    private final String value;

    public ExtraConst(@ExtraConst.Constants String value) {
        this.value = value;
    }


    @StringDef({
            Constants.LISTENER_ADDED,
            Constants.POD_NOTIF_STARTED,
            Constants.SOCKET_SERVER_ADDRESS,
            Constants.APP_ID,
            Constants.SERVER_NAME,
            Constants.TOKEN,
            Constants.SSO_HOST,
            Constants.DEVICE_ID,
            Constants.MESSAGE_DATA_KEY,
            Constants.PEER_ID_DATA_KEY,
            Constants.CONN_STATE,
            Constants.SERVICE_MESSAGE_EVENT,
            Constants.CHANNEL_CREATED,
            Constants.SERVICE_INSTANCE_ID_EVENT,
            Constants.SERVICE_RESTART_EVENT,
            Constants.NOTIFICATION_ACTION

    })

    @Retention(RetentionPolicy.SOURCE)
    public @interface Constants {

        String POD_NOTIF_STARTED = "POD_NOTIF_STARTED";
        String LISTENER_ADDED = "LISTENER_ADDED";
        String SOCKET_SERVER_ADDRESS = "SOCKET_SERVER_ADDRESS";
        String APP_ID = "APP_ID";
        String SERVER_NAME = "SERVER_NAME";
        String TOKEN = "TOKEN";
        String SSO_HOST = "SSO_HOST";
        String DEVICE_ID = "DEVICE_ID";
        String MESSAGE_DATA_KEY = "MESSAGE_DATA";
        String PEER_ID_DATA_KEY = "PEER_ID_DATA";
        String CONN_STATE = "CONNECTION_STATE";
        String SERVICE_MESSAGE_EVENT = "com.fanap.podnotify.MESSAGING_EVENT";
        String SERVICE_INSTANCE_ID_EVENT = "com.fanap.podnotify.INSTANCE_ID_EVENT";
        String SERVICE_RESTART_EVENT = "com.fanap.podnotify.RestartNotifService";
        String CHANNEL_CREATED = "CHANNEL_CREATED";
        String NOTIFICATION_ID = "NOTIFICATION_ID";
        String NOTIFICATION_SENDER_ID = "NOTIFICATION_SENDER_ID";
        String NOTIFICATION_ACTION = "NOTIFICATION_ACTION";
        String NOTIFICATION_ACTION_OPEN = "NOTIFICATION_OPEN";
        String NOTIFICATION_ACTION_DISMISS = "NOTIFICATION_DISMISS";
    }
}
