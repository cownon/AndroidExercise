package com.example.demomusicapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    private TextView tvTime, tvDuration;
    private SeekBar seekBarTime, seekBarVolume;
    private ImageView imgvolmunedown, imgvolumneup;
    private Button btnPlay;
    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Ánh xạ View
        tvTime = findViewById(R.id.tvTime);
        tvDuration = findViewById(R.id.tvDuration);
        seekBarTime = findViewById(R.id.seekBarTime);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        imgvolmunedown = findViewById(R.id.imavolumnedown);
        imgvolumneup = findViewById(R.id.Imgvolumneup);
        btnPlay = findViewById(R.id.btnPlay);

        // 2. Cài đặt sự kiện click
        btnPlay.setOnClickListener(this);
        imgvolmunedown.setOnClickListener(this);
        imgvolumneup.setOnClickListener(this);

        // 3. Khởi tạo MediaPlayer
        musicPlayer = MediaPlayer.create(this, R.raw.cochangtraivietlencay);
        tvDuration.setText(millisecondsToString(musicPlayer.getDuration()));
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);

        // Cài đặt SeekBar thời gian
        seekBarTime.setMax(musicPlayer.getDuration());

        // 4. Bắt sự kiện kéo SeekBar Volume
        seekBarVolume.setProgress(50);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float volume = i / 100f;
                musicPlayer.setVolume(volume, volume);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 5. Bắt sự kiện kéo SeekBar Time
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) {
                    musicPlayer.seekTo(i);
                    seekBar.setProgress(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // 6. Xử lý các nút bấm
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPlay) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.ic_play);
            } else {
                musicPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause);
                // Khởi chạy luồng cập nhật thời gian UI
                new Thread(this).start();
            }
        }
        else if (view.getId() == R.id.imavolumnedown) {
            musicPlayer.setVolume(0, 0);
            seekBarVolume.setProgress(0);
        }
        else if (view.getId() == R.id.Imgvolumneup) {
            musicPlayer.setVolume(1.0f, 1.0f);
            seekBarVolume.setProgress(100);
        }
    }

    // 7. Vòng lặp cập nhật SeekBar và Thời gian khi nhạc đang chạy
    @Override
    public void run() {
        int total = musicPlayer.getDuration();
        double currentPosition = musicPlayer.getCurrentPosition();

        while (musicPlayer != null && musicPlayer.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000); // Ngủ 1 giây
                final int current = musicPlayer.getCurrentPosition();
                final String elapsedTime = millisecondsToString(current);
                currentPosition = current;

                // Cập nhật giao diện (UI) phải thực hiện trên luồng chính
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText(elapsedTime);
                        seekBarTime.setProgress(current);
                    }
                });
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
        }
    }

    // Hàm chuyển đổi mili-giây sang định dạng mm:ss
    public String millisecondsToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = (time / 1000) % 60;
        elapsedTime = minutes + ":";
        if (seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;
        return elapsedTime;
    }

    // Đừng quên giải phóng bộ nhớ khi tắt app
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
    }
}