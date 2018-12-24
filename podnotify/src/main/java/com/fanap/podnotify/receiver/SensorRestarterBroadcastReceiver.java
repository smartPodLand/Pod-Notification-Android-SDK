package com.fanap.podnotify.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fanap.podnotify.service.NotifService;

/**
 * Created by arvin
 * on Mon, 24 December 2018 at 11:40 AM.
 * hi [at] arvinrokni [dot] ir
 */
public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, NotifService.class));
    }
}