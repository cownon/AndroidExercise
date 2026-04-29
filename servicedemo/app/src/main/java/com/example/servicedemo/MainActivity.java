package com.example.servicedemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.servicedemo.services.CalculatorService;
import com.example.servicedemo.services.DownloadService;
import com.example.servicedemo.services.MusicService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // === Music (Foreground) ===
    private TextView tvSongName, tvMusicTime;
    private SeekBar seekBarMusic;
    private Button btnPlay, btnPause, btnStopMusic;

    // === Download (Background) ===
    private TextView tvDownloadStatus, tvDownloadDetail;
    private ProgressBar progressDownload;
    private Button btnDownload;
    private StringBuilder downloadLog = new StringBuilder();

    // === Calculator (Bound) ===
    private TextView tvBoundStatus, tvCalcResult, tvCalcHistory;
    private EditText edtNum1, edtNum2;
    private Spinner spinnerOperator;
    private Button btnBind, btnUnbind, btnCalculate;
    private CalculatorService calcService;
    private boolean isBound = false;

    // === Log ===
    private TextView tvLog;
    private StringBuilder logBuilder = new StringBuilder();

    // ServiceConnection cho Calculator
    private ServiceConnection calcConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CalculatorService.CalculatorBinder binder =
                    (CalculatorService.CalculatorBinder) service;
            calcService = binder.getService();
            isBound = true;
            tvBoundStatus.setText("DA BIND - May tinh san sang!");
            tvBoundStatus.setTextColor(getColor(R.color.green));
            btnCalculate.setEnabled(true);
            appendLog("[BOUND] onServiceConnected - Calculator san sang");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            calcService = null;
            tvBoundStatus.setText("Mat ket noi");
            tvBoundStatus.setTextColor(getColor(R.color.red));
            btnCalculate.setEnabled(false);
            appendLog("[BOUND] onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        requestNotificationPermission();
        setupMusicControls();
        setupDownloadControls();
        setupCalculatorControls();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Listener cho Music Service
        MusicService.setListener(new MusicService.OnMusicListener() {
            @Override
            public void onMusicUpdate(String songName, String time, boolean isPlaying, int progress, int duration) {
                tvSongName.setText(songName + (isPlaying ? " (Dang phat)" : " (Tam dung)"));
                tvSongName.setTextColor(isPlaying ? getColor(R.color.green) : getColor(R.color.orange));
                tvMusicTime.setText(time);
                if (duration > 0) {
                    seekBarMusic.setMax(duration);
                    seekBarMusic.setProgress(progress);
                }
            }

            @Override
            public void onMusicStopped() {
                tvSongName.setText("Da dung phat nhac");
                tvSongName.setTextColor(getColor(R.color.red));
                tvMusicTime.setText("00:00 / 00:00");
                seekBarMusic.setProgress(0);
            }
        });

        // Listener cho Download Service
        DownloadService.setListener(new DownloadService.OnDownloadListener() {
            @Override
            public void onDownloadProgress(int current, int total, String fileName, String detail) {
                progressDownload.setProgress(current);
                tvDownloadStatus.setText("Dang tai: " + current + "/" + total);
                downloadLog.insert(0, detail + "\n");
                tvDownloadDetail.setText(downloadLog.toString());
            }

            @Override
            public void onDownloadComplete(int totalFiles, long totalSizeKB, String savedPath) {
                progressDownload.setProgress(5);
                tvDownloadStatus.setText("Hoan thanh! " + totalFiles + " anh (" + totalSizeKB + "KB)");
                tvDownloadStatus.setTextColor(getColor(R.color.green));
                downloadLog.insert(0, "=== HOAN THANH: " + totalFiles + " anh, " + totalSizeKB + "KB ===\n");
                downloadLog.insert(0, "Luu tai: " + savedPath + "\n");
                tvDownloadDetail.setText(downloadLog.toString());
                btnDownload.setEnabled(true);
                btnDownload.setText("TAI LAI 5 ANH");
                appendLog("[BACKGROUND] Download xong: " + totalFiles + " anh, " + totalSizeKB + "KB");
            }

            @Override
            public void onDownloadError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        MusicService.removeListener();
        DownloadService.removeListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(calcConnection);
            isBound = false;
        }
    }

    // ============================================================
    // FOREGROUND SERVICE - MUSIC
    // ============================================================

    private void setupMusicControls() {
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, MusicService.class);
            intent.setAction(MusicService.ACTION_PLAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
            appendLog("[FOREGROUND] PLAY - startForegroundService()");
        });

        btnPause.setOnClickListener(v -> {
            Intent intent = new Intent(this, MusicService.class);
            intent.setAction(MusicService.ACTION_PAUSE);
            startService(intent);
            btnPause.setText("RESUME");
            btnPause.setOnClickListener(v2 -> {
                Intent resumeIntent = new Intent(this, MusicService.class);
                resumeIntent.setAction(MusicService.ACTION_RESUME);
                startService(resumeIntent);
                btnPause.setText("PAUSE");
                setupPauseButton();
                appendLog("[FOREGROUND] RESUME");
            });
            appendLog("[FOREGROUND] PAUSE");
        });

        btnStopMusic.setOnClickListener(v -> {
            Intent intent = new Intent(this, MusicService.class);
            intent.setAction(MusicService.ACTION_STOP);
            startService(intent);
            setupPauseButton();
            appendLog("[FOREGROUND] STOP - stopSelf() => onDestroy()");
        });
    }

    private void setupPauseButton() {
        btnPause.setText("PAUSE");
        btnPause.setOnClickListener(v -> {
            Intent intent = new Intent(this, MusicService.class);
            intent.setAction(MusicService.ACTION_PAUSE);
            startService(intent);
            btnPause.setText("RESUME");
            btnPause.setOnClickListener(v2 -> {
                Intent resumeIntent = new Intent(this, MusicService.class);
                resumeIntent.setAction(MusicService.ACTION_RESUME);
                startService(resumeIntent);
                btnPause.setText("PAUSE");
                setupPauseButton();
                appendLog("[FOREGROUND] RESUME");
            });
            appendLog("[FOREGROUND] PAUSE");
        });
    }

    // ============================================================
    // BACKGROUND SERVICE - DOWNLOAD
    // ============================================================

    private void setupDownloadControls() {
        btnDownload.setOnClickListener(v -> {
            downloadLog = new StringBuilder();
            tvDownloadDetail.setText("");
            progressDownload.setProgress(0);
            tvDownloadStatus.setText("Bat dau tai...");
            tvDownloadStatus.setTextColor(getColor(R.color.orange));
            btnDownload.setEnabled(false);
            btnDownload.setText("DANG TAI...");

            Intent intent = new Intent(this, DownloadService.class);
            startService(intent);
            appendLog("[BACKGROUND] startService() - Tai 5 anh tu internet");
        });
    }

    // ============================================================
    // BOUND SERVICE - CALCULATOR
    // ============================================================

    private void setupCalculatorControls() {
        // Spinner phep tinh
        String[] operators = {"+", "-", "x", "/"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, operators);
        spinnerOperator.setAdapter(adapter);

        btnCalculate.setEnabled(false);

        // BIND
        btnBind.setOnClickListener(v -> {
            if (!isBound) {
                Intent intent = new Intent(this, CalculatorService.class);
                bindService(intent, calcConnection, Context.BIND_AUTO_CREATE);
                appendLog("[BOUND] bindService() => onCreate() => onBind()");
            } else {
                Toast.makeText(this, "Da bind roi!", Toast.LENGTH_SHORT).show();
            }
        });

        // UNBIND
        btnUnbind.setOnClickListener(v -> {
            if (isBound) {
                unbindService(calcConnection);
                isBound = false;
                calcService = null;
                tvBoundStatus.setText("Da UNBIND");
                tvBoundStatus.setTextColor(getColor(R.color.red));
                btnCalculate.setEnabled(false);
                tvCalcResult.setText("---");
                tvCalcHistory.setText("");
                appendLog("[BOUND] unbindService() => onUnbind() => onDestroy()");
            } else {
                Toast.makeText(this, "Chua bind!", Toast.LENGTH_SHORT).show();
            }
        });

        // TINH TOAN - Goi method cua Service (Client-Server)
        btnCalculate.setOnClickListener(v -> {
            if (!isBound || calcService == null) {
                Toast.makeText(this, "Chua bind Service!", Toast.LENGTH_SHORT).show();
                return;
            }

            String s1 = edtNum1.getText().toString().trim();
            String s2 = edtNum2.getText().toString().trim();

            if (s1.isEmpty() || s2.isEmpty()) {
                Toast.makeText(this, "Nhap 2 so!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double num1 = Double.parseDouble(s1);
                double num2 = Double.parseDouble(s2);
                String op = spinnerOperator.getSelectedItem().toString();

                // GOI METHOD CUA SERVICE TRUC TIEP (Client-Server pattern)
                double result = calcService.calculate(num1, op, num2);

                if (Double.isNaN(result)) {
                    tvCalcResult.setText("LOI!");
                    tvCalcResult.setTextColor(getColor(R.color.red));
                } else {
                    String resultStr = (result == (long) result)
                            ? String.valueOf((long) result)
                            : String.format("%.4f", result);
                    tvCalcResult.setText("= " + resultStr);
                    tvCalcResult.setTextColor(getColor(R.color.green));
                }

                // Hien thi lich su tu Service
                List<String> history = calcService.getHistory();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < Math.min(history.size(), 8); i++) {
                    sb.append(history.get(i)).append("\n");
                }
                tvCalcHistory.setText(sb.toString());

                appendLog("[BOUND] Tinh: " + s1 + " " + op + " " + s2 + " = " + tvCalcResult.getText());

            } catch (NumberFormatException e) {
                Toast.makeText(this, "So khong hop le!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ============================================================
    // HELPERS
    // ============================================================

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    private void initViews() {
        tvSongName = findViewById(R.id.tvSongName);
        tvMusicTime = findViewById(R.id.tvMusicTime);
        seekBarMusic = findViewById(R.id.seekBarMusic);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStopMusic = findViewById(R.id.btnStopMusic);

        tvDownloadStatus = findViewById(R.id.tvDownloadStatus);
        tvDownloadDetail = findViewById(R.id.tvDownloadDetail);
        progressDownload = findViewById(R.id.progressDownload);
        btnDownload = findViewById(R.id.btnDownload);

        tvBoundStatus = findViewById(R.id.tvBoundStatus);
        tvCalcResult = findViewById(R.id.tvCalcResult);
        tvCalcHistory = findViewById(R.id.tvCalcHistory);
        edtNum1 = findViewById(R.id.edtNum1);
        edtNum2 = findViewById(R.id.edtNum2);
        spinnerOperator = findViewById(R.id.spinnerOperator);
        btnBind = findViewById(R.id.btnBind);
        btnUnbind = findViewById(R.id.btnUnbind);
        btnCalculate = findViewById(R.id.btnCalculate);

        tvLog = findViewById(R.id.tvLog);
    }

    private void appendLog(String msg) {
        String time = java.text.DateFormat.getTimeInstance().format(new java.util.Date());
        logBuilder.insert(0, time + " " + msg + "\n");
        tvLog.setText(logBuilder.toString());
    }
}
