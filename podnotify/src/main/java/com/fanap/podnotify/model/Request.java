package com.fanap.podnotify.model;

/**
 * Created by ArvinRokni
 * on Sun, 16 December 2018 at 10:07 AM.
*/
public class Request {

    private String serviceName;
    private Integer messageType;
    private String content;


    public String getContent() {
        return content;
    }

    public Request setContent(String content) {
        this.content = content;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Request setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public Request setMessageType(Integer messageType) {
        this.messageType = messageType;
        return this;
    }
}
