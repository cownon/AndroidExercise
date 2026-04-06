package com.example.custombroadcastreceiver_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String MY_ACTION = "com.ptithcm.ACTION";
    private static final String MY_TEXT = "com.ptithcm.TEXT";
    private Button btnSendBroadcast;
    private TextView tvReceived;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MY_ACTION.equals(intent.getAction())) {
                String text = intent.getStringExtra(MY_TEXT);
                tvReceived.setText(text);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnSendBroadcast = findViewById(R.id.btn_send_broadcast);
        tvReceived = findViewById(R.id.tv_received);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Đã gửi Broadcast!", Toast.LENGTH_SHORT).show();
                clickSendBroadcast();
            }
        });
    }

    private void clickSendBroadcast() {
        Intent intent = new Intent(MY_ACTION);
        intent.putExtra(MY_TEXT, "This is test BroadcastReceived Custome");
        sendBroadcast(intent);
    }

    // Thêm @SuppressLint để tránh báo lỗi đỏ ở Android Studio mới
    @android.annotation.SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MY_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(mBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBroadcastReceiver);
    }
}