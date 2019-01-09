package com.fanap.podnotify.service;

import com.fanap.podnotify.service.abstracts.PodAbsMessagingService;
import com.fanap.podnotify.model.Content;
import com.fanap.podnotify.model.Notification;
import com.fanap.podnotify.util.PodServiceUtils;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
*/


public class PodMessagingService extends PodAbsMessagingService {

    @Override
    public void onMessageReceived(Notification notification) {
        PodServiceUtils.showNotification(getApplicationContext(), notification);
    }
}
