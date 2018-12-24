package com.fanap.podnotify.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fanap.podasync.Async;
import com.fanap.podasync.model.AsyncMessageType;
import com.fanap.podasync.util.JsonUtil;
import com.fanap.podnotify.PodNotificationListener;
import com.fanap.podnotify.PodNotify;
import com.fanap.podnotify.R;
import com.fanap.podnotify.model.Content;
import com.fanap.podnotify.model.Notification;
import com.fanap.podnotify.model.Request;

import java.util.Random;

import static com.fanap.podnotify.model.ExtraConst.Constants;

public class PodServiceUtils {

    private static final String TAG = PodServiceUtils.class.getSimpleName();
    private static final String DEFAULT_ID = "DEFAULT";

    public static void startService(Context context, Async async){
        if( async != null) {
                try {
                    if (async.getState() !=null && (async.getState().equals("ASYNC_READY") || async.getState().equals("OPEN") )) {
                        try {
                            async.logOut();
                        } catch (Exception ignored) {
                        }
                    }
                    async.connect(PodNotify.getSocketServerAddress(), PodNotify.getAppId(), PodNotify.getServerName(),
                            PodNotify.getToken(), PodNotify.getSsoHost(), PodNotify.getDeviceId());
                    async.addListener(new PodNotificationListener(context));
                } catch (Exception e) {
                    Log.e("AsyncConnect", e.getMessage());
                }
        }
    }

    public static void stopService(Async async) {
        if( async != null) {
            async.setReconnectOnClose(false);
            if (async.getState() !=null && (async.getState().equals("ASYNC_READY") || async.getState().equals("OPEN") )) {
                try {
                    async.logOut();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static void handShake(Context applicationContext, String messageId, String senderId, Content.Type type) {
        Async async = Async.getInstance(applicationContext);
        async.sendMessage(
                makeRequest(messageId,async.getPeerId(),senderId,type),
                AsyncMessageType.MessageType.MESSAGE_ACK_NEEDED);

        Log.i(TAG, "message sent.");
    }

    private static String makeRequest(String messageId, String receiverId, String senderId, Content.Type type) {

        return JsonUtil.getJson(new Request()
                .setContent(
                        JsonUtil.getJson(
                                new Content.builder()
                                        .setInfo(
                                                JsonUtil.getJson(
                                                        InfoUtils.creator()))
                                        .setMessageId(messageId)
                                        .setReceiverId(receiverId)
                                        .setSenderId(senderId)
                                        .setType(type.getValue())
                                        .setToken(PodNotify.getToken())
                                        .setAppId(PodNotify.getAppId())
                                        .setDeviceId(PodNotify.getDeviceId())
                                        .build()))
                .setMessageType(547)
                .setServiceName("SetStatusPush"));
    }

    public static int showNotification(Context context,Notification notification) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O
                && !SharedPref.getInstance(context).getBoolean(Constants.CHANNEL_CREATED,false)) {

            SharedPref.getInstance(context).edit().putBoolean(Constants.CHANNEL_CREATED,true).apply();

            NotificationChannel channel = new NotificationChannel(DEFAULT_ID,
                    "DEFAULT", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        android.app.Notification notificationCompat = new NotificationCompat.Builder(context, DEFAULT_ID)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getText())
                .setExtras(new Bundle((ClassLoader) notification.getExtras()))
                .setSmallIcon(R.drawable.notification)
                .build();

        int notificationId = new Random().nextInt(1000);
        manager.notify(notificationId, notificationCompat);
        return notificationId;
    }

    public static void callOnMessageReceived(Context context,ServiceConnection connection,String textMessage) {
        Intent intent = new Intent(Constants.SERVICE_MESSAGE_EVENT);
        intent.setPackage(context.getPackageName());
        intent.putExtra(Constants.MESSAGE_DATA_KEY, textMessage);
        try {
            context.unbindService(connection);
        } catch (Exception ignored){}
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public static void callOnPeerIdChanged(Context context,ServiceConnection connection, String peerId) {

        Intent intent = new Intent(Constants.SERVICE_INSTANCE_ID_EVENT);
        intent.setPackage(context.getPackageName());
        intent.putExtra(Constants.PEER_ID_DATA_KEY, peerId);

        SharedPref.getInstance(context).edit()
                .putString(Constants.PEER_ID_DATA_KEY, peerId).apply();
        try {
            context.unbindService(connection);
        } catch (Exception ignored){}
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    public static void doFirstTimeHandShake(Context context) {
        handShake(context,null,null,Content.Type.FIRST_TIME);
    }
}
