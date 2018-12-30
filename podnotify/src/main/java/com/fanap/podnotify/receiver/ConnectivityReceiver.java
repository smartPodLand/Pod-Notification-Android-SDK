package com.fanap.podnotify.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ArvinRokni
 * on Mon, 17 December 2018 at 12:45 PM.
*/


public class ConnectivityReceiver extends BroadcastReceiver {

    private ConnectivityReceiverListener mConnectivityReceiverListener;

   public ConnectivityReceiver() {
    }

    public ConnectivityReceiver(ConnectivityReceiverListener listener) {
        mConnectivityReceiverListener = listener;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        mConnectivityReceiverListener.onNetworkConnectionChanged(isConnected(context));

    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
