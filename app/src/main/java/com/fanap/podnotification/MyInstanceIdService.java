package com.fanap.podnotification;

import android.util.Log;

import com.fanap.podnotify.service.PodInstanceIdService;

public class MyInstanceIdService extends PodInstanceIdService {

    @Override
    public void onPeerIdChanged(String peerId) {

        Log.i("PEER_ID", String.valueOf(peerId));
        super.onPeerIdChanged(peerId);
    }
}
