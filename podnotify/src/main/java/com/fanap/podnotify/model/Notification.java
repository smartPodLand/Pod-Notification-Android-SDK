package com.fanap.podnotify.model;

import java.util.Map;

/**
 * Created by arvin
 * on Sun, 16 December 2018 at 9:59 AM.
 * hi [at] arvinrokni [dot] ir
 */
public class Notification {

    private String title;
    private String text;
    private Long messageId;
    private String senderId;
    private Map<String,String> extras;
    private int pushNotificationMessageType;

    public String getTitle() {
        return title;
    }

    public Notification setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Notification setText(String text) {
        this.text = text;
        return this;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Notification setMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public Notification setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public Notification setExtras(Map<String, String> extras) {
        this.extras = extras;
        return this;
    }

    public int getPushNotificationMessageType() {
        return pushNotificationMessageType;
    }

    public Notification setPushNotificationMessageType(int pushNotificationMessageType) {
        this.pushNotificationMessageType = pushNotificationMessageType;
        return this;
    }
}
