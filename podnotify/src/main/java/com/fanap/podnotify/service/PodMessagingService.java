package com.fanap.podnotify.service;

import com.fanap.podnotify.service.abstracts.PodAbsMessagingService;
import com.fanap.podnotify.model.Content;
import com.fanap.podnotify.model.Notification;
import com.fanap.podnotify.util.PodServiceUtils;

public class PodMessagingService extends PodAbsMessagingService {

    @Override
    public void onMessageReceived(Notification notification) {
        String messageId = String.valueOf(notification.getMessageId());
        String senderId = notification.getSenderId();

        PodServiceUtils.handShake(getApplicationContext(), messageId, senderId, Content.Type.DELIVERD);
        PodServiceUtils.showNotification(getApplicationContext(), notification);
    }
}
