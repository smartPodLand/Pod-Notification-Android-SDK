package com.fanap.podnotify.service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;

import com.fanap.podasync.Async;
import com.fanap.podnotify.util.PodServiceUtils;

/**
 * Created by arvin
 * on Mon, 24 December 2018 at 11:40 AM.
 * hi [at] arvinrokni [dot] ir
 */

@SuppressLint("NewApi")
public class JobNotifService extends JobService {

    private Async async;

    public JobNotifService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PodServiceUtils.startService(getApplicationContext(),async);
            }
        }).start();

        return true;
    }




    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PodServiceUtils.stopService(async);
            }
        }).start();
        jobFinished(jobParameters,true);
        return true;
    }

    @Override
    public void onCreate() {
        async = Async.getInstance(getApplicationContext());
    }


}
