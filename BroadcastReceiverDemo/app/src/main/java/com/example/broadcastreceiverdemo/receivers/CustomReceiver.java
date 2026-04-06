package com.example.broadcastreceiverdemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CustomReceiver extends BroadcastReceiver {
    public static final String CUSTOM_ACTION = "com.example.broadcastdemo.CUSTOM_ACTION";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String TAG = "CustomReceiver";

    public interface OnCustomBroadcastListener {
        void onCustomBroadcastReceived(String message);
    }
    private OnCustomBroadcastListener listener;

    public CustomReceiver() {}

    public CustomReceiver(OnCustomBroadcastListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (CUSTOM_ACTION.equals(intent.getAction())) {
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            Log.d(TAG, "Nhận được: " + message);
            if (listener != null) {
                listener.onCustomBroadcastReceived(message);
            }
        }
    }
}