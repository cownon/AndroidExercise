package com.example.broadcastreceiverdemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkChangeReceiver";

    public interface OnNetworkChangeListener {
        void onNetworkChanged(boolean isConnected, String networkType);
    }
    private OnNetworkChangeListener listener;

    public NetworkChangeReceiver() {}

    public NetworkChangeReceiver(OnNetworkChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null && activeNetwork.isConnected());
        String networkType = "Khong co mang";

        if (isConnected) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                networkType = "WiFi";
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                networkType = "Mobile Data";
            } else {
                networkType = "Khac";
            }
        }
        Log.d(TAG, "Mang thay doi: " + networkType + " | Connected: " + isConnected);
        if (listener != null) {
            listener.onNetworkChanged(isConnected, networkType);
        }
    }
}