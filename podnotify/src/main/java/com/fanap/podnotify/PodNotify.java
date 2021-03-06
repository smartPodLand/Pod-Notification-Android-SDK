package com.fanap.podnotify;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.fanap.podasync.Async;
import com.fanap.podasync.AsyncListener;
import com.fanap.podnotify.receiver.StartServiceReceiver;
import com.fanap.podnotify.service.JobNotifService;
import com.fanap.podnotify.service.NetworkSchedulerService;
import com.fanap.podnotify.service.NotifService;
import com.fanap.podnotify.util.SharedPref;


import java.io.IOException;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
*/

public class PodNotify {

    private static final String TAG = PodNotify.class.getSimpleName();
    private static final int NOTIF_JOB_ID = 604;
    private static final int SCHEDULER_JOB_ID = 858;

    private static PodNotify instance;

    public void stop(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.cancel(NOTIF_JOB_ID);
        } else {
            Intent serviceIntent = new Intent(context, NotifService.class);
            context.stopService(serviceIntent);
        }
    }

    public static class builder{
        private String socketServerAddress;
        private String appId;
        private String serverName;
        private String token;
        private String ssoHost;
        private String deviceId;

        public builder setSocketServerAddress(String socketServerAddress) {
            this.socketServerAddress = socketServerAddress;
            return this;
        }

        public builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public builder setServerName(String serverName) {
            this.serverName = serverName;
            return this;
        }

        public builder setToken(String token) {
            this.token = token;
            return this;
        }

        public builder setSsoHost(String ssoHost) {
            this.ssoHost = ssoHost;
            return this;
        }

        public builder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public PodNotify build(Context context){
            return new PodNotify(context,socketServerAddress,appId,serverName,token,ssoHost,deviceId);
        }
    }

    private PodNotify(Context context, String socketServerAddress, String appId, String serverName,
                     String token, String ssoHost,String deviceId) {
        SharedPreferences sharedPref = SharedPref.getInstance(context);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        sharedPrefEditor.putString("socketServerAddress",socketServerAddress);
        sharedPrefEditor.putString("appId", appId);
        sharedPrefEditor.putString("serverName", serverName);
        sharedPrefEditor.putString("token", token);
        sharedPrefEditor.putString("ssoHost", ssoHost);
        sharedPrefEditor.putString("deviceId", deviceId);
        sharedPrefEditor.apply();
    }

    public void start(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                scheduleService(context);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    scheduleNetworkService(context);
//                }
            }
        }).start();
    }

    private void scheduleService(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Intent serviceIntent = new Intent(context, NotifService.class);
            context.stopService(serviceIntent);
            context.startService(serviceIntent);
        } else {
            ComponentName componentName = new ComponentName(context, JobNotifService.class);
            JobInfo jobInfo = new JobInfo.Builder(NOTIF_JOB_ID, componentName)
                    .setPersisted(true)
                    .setMinimumLatency(1000)
                    .setOverrideDeadline(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleNetworkService(Context context) {
        JobInfo myJob = new JobInfo.Builder(SCHEDULER_JOB_ID, new ComponentName(context, NetworkSchedulerService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(SCHEDULER_JOB_ID);
        jobScheduler.schedule(myJob);

    }

    public static String getSocketServerAddress(Context context) {
        SharedPreferences sharedPref = SharedPref.getInstance(context);
        return sharedPref.getString("socketServerAddress",null);
    }

    public static String getAppId(Context context) {
        SharedPreferences sharedPref = SharedPref.getInstance(context);
        return sharedPref.getString("appId", null);
    }

    public static String getServerName(Context context) {
        SharedPreferences sharedPref = SharedPref.getInstance(context);
        return sharedPref.getString("serverName", null);
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPref = SharedPref.getInstance(context);
        return sharedPref.getString("token",null);
    }

    public static String getSsoHost(Context context) {
        SharedPreferences sharedPref = SharedPref.getInstance(context);
        return sharedPref.getString("ssoHost",null);
    }

    public static String getDeviceId(Context context) {
        SharedPreferences sharedPref = SharedPref.getInstance(context);
        return sharedPref.getString("deviceId",null);
    }

    public static void setApplication(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            IntentFilter intentFilterNotif = new IntentFilter();
            intentFilterNotif.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            intentFilterNotif.addAction("android.intent.action.BOOT_COMPLETED");
            context.getApplicationContext().registerReceiver(new StartServiceReceiver(), intentFilterNotif);
        }
    }

    public String getPeerId(Context context){
        return Async.getInstance(context).getPeerId();
    }

    public LiveData<String> getState(Context context){

        final MutableLiveData<String> liveData = new MutableLiveData<>();

        Async.getInstance(context).addListener(new AsyncListener() {
            @Override
            public void onReceivedMessage(String textMessage) throws IOException {
                Log.wtf("onReceivedMessage", textMessage);

            }

            @Override
            public void onStateChanged(String state) throws IOException {
                liveData.postValue(state);
            }

            @Override
            public void onDisconnected(String textMessage) throws IOException {
                Log.wtf("onDisconnected", textMessage);

            }

            @Override
            public void onError(String textMessage) throws IOException {
                Log.wtf("onError", textMessage);
            }

            @Override
            public void handleCallbackError(Throwable cause) throws Exception {
                Log.wtf("handleCallbackError", cause);
            }
        });

        return liveData;
    }
}
