package com.fanap.podnotify.service;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;

import com.fanap.podasync.Async;
import com.fanap.podnotify.PodNotificationListener;
import com.fanap.podnotify.model.ExtraConst;
import com.fanap.podnotify.model.Notification;
import com.fanap.podnotify.util.PodServiceUtils;
import com.fanap.podnotify.util.SharedPref;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
 */


@SuppressLint("NewApi")
public class JobNotifService extends JobService {

    private PodServiceUtils serviceUtils;

    private static final int SCHEDULER_JOB_ID = 858;

    public JobNotifService() {
    }


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceUtils.startService(getApplicationContext());
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters jobParameters) {

        jobFinished(jobParameters,true);
        return true;
    }

    @Override
    public void onCreate() {
        Async async = Async.getInstance(JobNotifService.this);
        PodNotificationListener podNotificationListener = new PodNotificationListener(JobNotifService.this);
        serviceUtils = new PodServiceUtils(async, podNotificationListener);
    }

    @Override
    public void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceUtils.stopService();
                reopen(getApplicationContext());
            }
        }).start();

        super.onDestroy();
    }

    private void reopen(Context context) {

        JobInfo myJob = new JobInfo.Builder(SCHEDULER_JOB_ID, new ComponentName(context, NetworkSchedulerService.class))
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        assert jobScheduler != null;
        jobScheduler.cancelAll();
        jobScheduler.schedule(myJob);
    }


}
