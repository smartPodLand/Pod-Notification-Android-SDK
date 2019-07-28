package com.fanap.podnotify.service;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import com.fanap.podnotify.receiver.ConnectivityReceiver;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
 */


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NetworkSchedulerService extends JobService implements
        ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();
    private static final int NOTIF_JOB_ID = 604;

    private ConnectivityReceiver mConnectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mConnectivityReceiver = new ConnectivityReceiver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
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
        jobFinished(params,true);
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected)
            task(getApplicationContext());
    }

    private void task(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Intent serviceIntent = new Intent(context, NotifService.class);
            context.stopService(serviceIntent);
            context.startService(serviceIntent);
        } else {
            ComponentName componentName = new ComponentName(context, JobNotifService.class);
            JobInfo jobInfo = new JobInfo.Builder(NOTIF_JOB_ID, componentName)
                    .setPersisted(true)
                    .setMinimumLatency(1000)
                    .build();

            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int resultCode = 0;
            if (jobScheduler != null) {
                jobScheduler.cancel(NOTIF_JOB_ID);
                resultCode = jobScheduler.schedule(jobInfo);
            }
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "NotifJob scheduled!");
            } else {
                Log.d(TAG, "NotifJob not scheduled");
            }
        }
    }
}
