package com.fanap.podnotify.receiver;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.fanap.podnotify.service.JobNotifService;
import com.fanap.podnotify.service.NotifService;

/**
 * Created by arvin
 * on Mon, 24 December 2018 at 11:40 AM.
 * hi [at] arvinrokni [dot] ir
 */

public class StartServiceReciver extends BroadcastReceiver {

    private static final int NOTIF_JOB_ID = 604;


    private boolean isMyServiceRunning(Context context,Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (!isMyServiceRunning(context,NotifService.class)) {
                Intent serviceIntent = new Intent(context, NotifService.class);
                context.startService(serviceIntent);
            }
        } else {

            ComponentName componentName = new ComponentName(context, JobNotifService.class);
            JobInfo jobInfo = new JobInfo.Builder(NOTIF_JOB_ID, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .setMinimumLatency(1000)
                    .build();
            JobScheduler jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            if (jobScheduler != null && !jobScheduler.getAllPendingJobs().contains(jobInfo)) {
                jobScheduler.schedule(jobInfo);
            }
        }

    }
}
