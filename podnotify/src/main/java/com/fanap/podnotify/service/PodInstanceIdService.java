package com.fanap.podnotify.service;

import com.fanap.podnotify.service.abstracts.PodAbsInstanceIdService;

/**
 * Created by arvin
 * on Mon, 17 December 2018 at 12:45 PM.
 * hi [at] arvinrokni [dot] ir
 */


public class PodInstanceIdService extends PodAbsInstanceIdService {

    @Override
    public void onPeerIdChanged(String peerId) {
        //TODO: some automation for user notif!
//        stopSelf();
    }


}
