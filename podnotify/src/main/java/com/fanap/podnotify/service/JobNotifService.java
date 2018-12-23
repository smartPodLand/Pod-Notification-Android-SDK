package com.fanap.podnotify.service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;

import com.fanap.podasync.Async;
import com.fanap.podnotify.util.PodServiceUtils;

@SuppressLint("NewApi")
public class JobNotifService extends JobService {

    private Async async;

    public JobNotifService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        PodServiceUtils.startService(getApplicationContext(),async);

        return true;
    }




    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        PodServiceUtils.stopService(async);

//        jobFinished(jobParameters,true);

        return false;
    }

    @Override
    public void onCreate() {
        async = Async.getInstance(getApplicationContext());
    }


}
