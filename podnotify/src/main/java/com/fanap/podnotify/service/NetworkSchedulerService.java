package com.fanap.podnotify.service;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import com.fanap.podnotify.receiver.ConnectivityReceiver;
import com.fanap.podnotify.receiver.StartServiceReciver;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
 */


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NetworkSchedulerService extends JobService implements
        ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private ConnectivityReceiver mConnectivityReceiver;
    AlarmManager alarmManager;
    @Override
    public void onCreate() {
        super.onCreate();
        mConnectivityReceiver = new ConnectivityReceiver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        } catch (Exception ignored){
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(mConnectivityReceiver);
        } catch (Exception ignored){}
        super.onDestroy();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        try {
            registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }catch (Exception ignored){}
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        try {
            unregisterReceiver(mConnectivityReceiver);
        } catch (Exception ignored){}
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected)
            task();
    }

    private void task() {
        Intent intent1 = new Intent(getApplicationContext(), StartServiceReciver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+100,pendingIntent1);
    }
}
