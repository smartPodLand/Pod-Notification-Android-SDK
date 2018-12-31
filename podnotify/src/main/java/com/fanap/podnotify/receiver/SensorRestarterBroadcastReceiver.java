package com.fanap.podnotify.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fanap.podnotify.service.NotifService;

/**
 * Created by ArvinRokni
 * on Mon, 24 December 2018 at 11:40 AM.
*/
public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {

    private static final int REQ_COD = 48;
    private static final int DELAY = 30 * 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotifService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(),REQ_COD,i,Intent.FILL_IN_ACTION);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + DELAY, pendingIntent);
        }
    }
}