package com.example.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ============================================================
 * BACKGROUND SERVICE - TAI ANH TU INTERNET (Slide 4)
 * ============================================================
 * Cong viec CU THE:
 * - Tai 5 anh tu internet (picsum.photos - anh random)
 * - Luu vao bo nho trong cua app
 * - Hien thi ten file, kich thuoc, tien trinh tung anh
 * - Tu dung (stopSelf) khi tai xong tat ca
 *
 * Nguoi dung thay duoc:
 * - Tien trinh: "Dang tai anh 2/5..."
 * - Ket qua: "image_1.jpg (45KB) - Da luu"
 * - Tong ket: "Tai xong 5 anh, tong 230KB"
 * ============================================================
 */
public class DownloadService extends Service {

    private static final String TAG = "DownloadService";
    private static final int TOTAL_IMAGES = 20;
    private static final String BASE_URL = "https://picsum.photos/400/300";

    private Handler handler;
    private boolean isRunning = false;

    private static OnDownloadListener listener;

    public interface OnDownloadListener {
        void onDownloadProgress(int current, int total, String fileName, String detail);
        void onDownloadComplete(int totalFiles, long totalSizeKB, String savedPath);
        void onDownloadError(String error);
    }

    public static void setListener(OnDownloadListener l) { listener = l; }
    public static void removeListener() { listener = null; }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() - Bat dau tai anh");

        if (isRunning) {
            postToMain(() -> {
                if (listener != null) listener.onDownloadError("Dang tai roi, vui long doi...");
            });
            return START_NOT_STICKY;
        }

        startDownloading();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.d(TAG, "onDestroy()");
    }

    // ============================================================
    // TAI ANH TU INTERNET
    // ============================================================

    private void startDownloading() {
        isRunning = true;

        new Thread(() -> {
            File downloadDir = new File(getFilesDir(), "downloads");
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }

            long totalSize = 0;
            int successCount = 0;

            for (int i = 1; i <= TOTAL_IMAGES; i++) {
                if (!isRunning) break;

                final int index = i;
                final String fileName = "image_" + i + ".jpg";

                // Thong bao dang tai
                postToMain(() -> {
                    if (listener != null) {
                        listener.onDownloadProgress(index, TOTAL_IMAGES, fileName,
                                "Dang tai anh " + index + "/" + TOTAL_IMAGES + "...");
                    }
                });

                try {
                    // Tai anh tu picsum.photos (random image)
                    URL url = new URL(BASE_URL + "?random=" + i + "&t=" + System.currentTimeMillis());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setInstanceFollowRedirects(true);
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    conn.connect();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream input = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        input.close();

                        if (bitmap != null) {
                            // Luu anh vao file
                            File file = new File(downloadDir, fileName);
                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
                            fos.flush();
                            fos.close();

                            long fileSize = file.length();
                            totalSize += fileSize;
                            successCount++;

                            final long fileSizeKB = fileSize / 1024;
                            final int width = bitmap.getWidth();
                            final int height = bitmap.getHeight();
                            bitmap.recycle();

                            // Thong bao da tai xong 1 anh
                            postToMain(() -> {
                                if (listener != null) {
                                    listener.onDownloadProgress(index, TOTAL_IMAGES, fileName,
                                            fileName + " (" + width + "x" + height + ", " + fileSizeKB + "KB) - Da luu!");
                                }
                            });

                            Log.d(TAG, "Da tai: " + fileName + " - " + fileSizeKB + "KB");
                        }
                    }
                    conn.disconnect();

                    // Doi 1 chut giua cac lan tai (de thay ro tien trinh)
                    Thread.sleep(800);

                } catch (Exception e) {
                    Log.e(TAG, "Loi tai " + fileName + ": " + e.getMessage());
                    final String error = fileName + " - Loi: " + e.getMessage();
                    postToMain(() -> {
                        if (listener != null) {
                            listener.onDownloadProgress(index, TOTAL_IMAGES, fileName, error);
                        }
                    });
                }
            }

            // Hoan thanh tat ca
            final int finalSuccess = successCount;
            final long finalTotalKB = totalSize / 1024;
            final String savedPath = downloadDir.getAbsolutePath();

            postToMain(() -> {
                if (listener != null) {
                    listener.onDownloadComplete(finalSuccess, finalTotalKB, savedPath);
                }
            });

            Log.d(TAG, "Hoan thanh! " + finalSuccess + " anh, " + finalTotalKB + "KB");

            // Tu dung service (Slide 8)
            isRunning = false;
            stopSelf();

        }).start();
    }

    private void postToMain(Runnable runnable) {
        handler.post(runnable);
    }
}
