package com.fanap.podnotify.receiver;

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

public class StartServiceReciver extends BroadcastReceiver {
//    @SuppressLint("WrongConstant")
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent serviceIntent = new Intent(context, NotifService.class);
            context.stopService(serviceIntent);
            context.startService(serviceIntent);
        } else {

            ComponentName componentName = new ComponentName(context, JobNotifService.class);
            JobInfo jobInfo = new JobInfo.Builder(12, componentName)
                    .setPersisted(true)
                    .setMinimumLatency(1000)
                    .build();
            JobScheduler jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int resultCode = 0;
            if (jobScheduler != null && !jobScheduler.getAllPendingJobs().contains(jobInfo)) {
                resultCode = jobScheduler.schedule(jobInfo);
            }
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d("job", "Job scheduled!");
            } else {
                Log.d("job", "Job not scheduled");
            }
        }

    }
}
