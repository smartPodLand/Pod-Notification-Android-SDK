package com.fanap.podnotify.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by arvin
 * on Sun, 09 December 2018 at 5:57 PM.
 * hi [at] arvinrokni [dot] ir
 */
public abstract class ExtraConst {

    private final String value;

    public ExtraConst(@ExtraConst.Constants String value) {
        this.value = value;
    }


    @StringDef({
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
            Constants.ASYNC_CLOSED,
            Constants.SERVICE_INSTANCE_ID_EVENT

    })

    @Retention(RetentionPolicy.SOURCE)
    public @interface Constants {
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
        String CHANNEL_CREATED = "CHANNEL_CREATED";
        String ASYNC_CLOSED = "ASYNC_CLOSED";
    }
}
