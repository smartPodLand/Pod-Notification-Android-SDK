package com.fanap.podnotify.model;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
*/


public class Content {

    private int type;
    private String messageId;
    private String senderId;
    private String receiverId;
    private String token;
    private String info;
    private String appId;
    private String deviceId;
    private String sdkType ;


    public String getSdkType() {
        return sdkType;
    }

    public void setSdkType(String sdkType) {
        this.sdkType = sdkType;
    }

    public enum Type{
        FIRST_TIME(0),
        DELIVERD(1),
        OPEN(2),
        DISMISS(3);

        int val;

        Type(int val) {
            this.val = val;
        }

        public int getValue() {
            return val;
        }
    }


    public Content(int type, String messageId, String senderId, String receiverId, String info,
                   String token, String appId, String deviceId,String sdkType) {
        this.type = type;
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.info = info;
        this.token = token;
        this.appId = appId;
        this.deviceId = deviceId;
        this.sdkType = sdkType;

    }

    public Content() {
    }

    public static class builder{

        private int type;
        private String messageId;
        private String senderId;
        private String receiverId;
        private String info;
        private String token;
        private String appId;
        private String deviceId;

        public builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public builder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public builder setType(int type) {
            this.type = type;
            return this;
        }

        public builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public builder setSenderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public builder setReceiverId(String receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public builder setInfo(String info) {
            this.info = info;
            return this;
        }

        public builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Content build(){
            return new Content(type,messageId,senderId,receiverId,info,token,appId,deviceId,"ANDROID");
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
