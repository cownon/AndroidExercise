package com.example.servicedemo.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.servicedemo.MainActivity;
import com.example.servicedemo.R;

import java.util.Locale;

/**
 * ============================================================
 * FOREGROUND SERVICE - PHAT NHAC (Slide 3)
 * ============================================================
 */
public class MusicService extends Service {

    private static final String TAG = "MusicService";
    private static final String CHANNEL_ID = "MusicServiceChannel";
    private static final int NOTIFICATION_ID = 1;

    public static final String ACTION_PLAY = "ACTION_PLAY";
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    public static final String ACTION_RESUME = "ACTION_RESUME";
    public static final String ACTION_STOP = "ACTION_STOP";

    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable updateRunnable;
    private boolean isPaused = false;

    // Callback ve Activity
    private static OnMusicListener listener;

    public interface OnMusicListener {
        void onMusicUpdate(String songName, String time, boolean isPlaying, int progress, int duration);
        void onMusicStopped();
    }

    public static void setListener(OnMusicListener l) { listener = l; }
    public static void removeListener() { listener = null; }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        handler = new Handler(Looper.getMainLooper());
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : ACTION_PLAY;

        if (ACTION_STOP.equals(action)) {
            stopMusic();
            stopForeground(STOP_FOREGROUND_REMOVE);
            stopSelf();
            return START_NOT_STICKY;
        }

        if (ACTION_PAUSE.equals(action)) {
            pauseMusic();
            return START_STICKY;
        }

        if (ACTION_RESUME.equals(action)) {
            resumeMusic();
            return START_STICKY;
        }

        startMusic();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        stopMusic();
    }

    private void startMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.cochangtraivietlencay);

        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            isPaused = false;

            Notification notification = buildNotification("Đang phát nhạc...", true);
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
            } else {
                startForeground(NOTIFICATION_ID, notification);
            }

            startUpdating();
        } else {
            Log.e(TAG, "Không thể khởi tạo MediaPlayer!");
            if (listener != null) {
                listener.onMusicUpdate("Lỗi: Không tìm thấy nhạc", "00:00", false, 0, 0);
            }
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
            updateNotification("Tạm dừng", false);
            notifyListener();
        }
    }

    private void resumeMusic() {
        if (mediaPlayer != null && isPaused) {
            mediaPlayer.start();
            isPaused = false;
            updateNotification("Đang phát nhạc...", true);
        }
    }

    private void stopMusic() {
        if (handler != null && updateRunnable != null) {
            handler.removeCallbacks(updateRunnable);
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPaused = false;
        if (listener != null) {
            listener.onMusicStopped();
        }
    }

    private void startUpdating() {
        if (updateRunnable != null) {
            handler.removeCallbacks(updateRunnable);
        }
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && !isPaused) {
                    notifyListener();
                    updateNotification("Đang phát: " + getCurrentTimeStr(), true);
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateRunnable);
    }

    private void notifyListener() {
        if (listener != null && mediaPlayer != null) {
            int current = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            String timeStr = formatMs(current) + " / " + formatMs(duration);
            boolean playing = mediaPlayer.isPlaying();

            listener.onMusicUpdate(
                    "Có chàng trai viết lên cây",
                    timeStr,
                    playing,
                    current,
                    duration
            );
        }
    }

    private String getCurrentTimeStr() {
        if (mediaPlayer == null) return "00:00";
        return formatMs(mediaPlayer.getCurrentPosition());
    }

    private String formatMs(int ms) {
        int sec = (ms / 1000) % 60;
        int min = (ms / 1000) / 60;
        return String.format(Locale.getDefault(), "%02d:%02d", min, sec);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Music Player", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Điều khiển nhạc");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildNotification(String text, boolean isPlaying) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        int icon = isPlaying ? android.R.drawable.ic_media_play : android.R.drawable.ic_media_pause;

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Service Demo")
                .setContentText(text)
                .setSmallIcon(icon)
                .setContentIntent(pi)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    private void updateNotification(String text, boolean isPlaying) {
        Notification notification = buildNotification(text, isPlaying);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }
}