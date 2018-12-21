package com.fanap.podnotify.service;

import com.fanap.podnotify.service.abstracts.PodAbsInstanceIdService;

public class PodInstanceIdService extends PodAbsInstanceIdService {

    @Override
    public void onPeerIdChanged(String peerId) {
        //TODO: some automation for user notif!
//        stopSelf();
    }


}
