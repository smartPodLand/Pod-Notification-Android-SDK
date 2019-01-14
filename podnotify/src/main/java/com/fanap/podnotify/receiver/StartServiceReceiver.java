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

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
*/

public class StartServiceReceiver extends BroadcastReceiver {

    private static final int NOTIF_JOB_ID = 604;

    @Override
    public void onReceive(Context context, Intent intent) {

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

            jobScheduler.cancel(NOTIF_JOB_ID);
            int resultCode = jobScheduler.schedule(jobInfo);

            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d("job", "Job scheduled!");
            } else {
                Log.d("job", "Job not scheduled");
            }
        }

    }
}
