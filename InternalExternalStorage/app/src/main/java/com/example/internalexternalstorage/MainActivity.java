package com.example.internalexternalstorage;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internalexternalstorage.R;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    String myInternalStorage = "internal.txt";
    String myCache = "cache.txt";
    String myExternalStorage = "External.txt";
    String data = "Hello Android Storage!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BUTTONS
        Button btnWriteInternal = findViewById(R.id.btnWriteInternal);
        Button btnReadInternal = findViewById(R.id.btnReadInternal);
        Button btnWriteCache = findViewById(R.id.btnWriteCache);
        Button btnReadCache = findViewById(R.id.btnReadCache);
        Button btnWriteExternal = findViewById(R.id.btnWriteExternal);
        Button btnReadExternal = findViewById(R.id.btnReadExternal);

        // WRITE INTERNAL
        btnWriteInternal.setOnClickListener(v -> {
            try {
                FileOutputStream fos = openFileOutput(myInternalStorage, MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();
                showToast("Write Internal thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // READ INTERNAL
        btnReadInternal.setOnClickListener(v -> {
            try {
                FileInputStream fis = openFileInput(myInternalStorage);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line = reader.readLine();
                reader.close();
                showToast("Read Internal: " + line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // WRITE CACHE
        btnWriteCache.setOnClickListener(v -> {
            try {
                File file = new File(getCacheDir(), myCache);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data.getBytes());
                fos.close();
                showToast("Write Cache thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // READ CACHE
        btnReadCache.setOnClickListener(v -> {
            try {
                File file = new File(getCacheDir(), myCache);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                reader.close();
                showToast("Read Cache: " + line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // WRITE EXTERNAL
        btnWriteExternal.setOnClickListener(v -> {
            try {
                File dir = getExternalFilesDir(null);
                File file = new File(dir, myExternalStorage);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data.getBytes());
                fos.close();
                showToast("Write External thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // READ EXTERNAL
        btnReadExternal.setOnClickListener(v -> {
            try {
                File dir = getExternalFilesDir(null);
                File file = new File(dir, myExternalStorage);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                reader.close();
                showToast("Read External: " + line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}