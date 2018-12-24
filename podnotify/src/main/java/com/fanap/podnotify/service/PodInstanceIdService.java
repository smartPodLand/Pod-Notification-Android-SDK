package com.fanap.podnotify.service;

import com.fanap.podnotify.service.abstracts.PodAbsInstanceIdService;

/**
 * Created by arvin
 * on Mon, 24 December 2018 at 11:40 AM.
 * hi [at] arvinrokni [dot] ir
 */

public class PodInstanceIdService extends PodAbsInstanceIdService {

    @Override
    public void onPeerIdChanged(String peerId) {
        //TODO: some automation for user notif!
//        stopSelf();
    }


}
