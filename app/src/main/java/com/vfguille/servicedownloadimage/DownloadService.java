package com.vfguille.servicedownloadimage;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DownloadService extends Service {

    private static final String DOWNLOAD_PATH = "com.vfguille.servicedownloadimage_DownloadSongService_Download_path";
    private static final String DESTINATION_PATH = "com.vfguille.servicedownloadimage_DownloadSongService_Destination_path";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Se ejecuta el código en un Async Task.
        Toast.makeText(this, "Servicio iniciado", Toast.LENGTH_SHORT).show();
        new DownloadImageTask().execute(intent.getExtras().getString("urlPath"));



        return START_STICKY;
    }

    /*
        Descarga la imagen en una tarea asíncrona.
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Long>{

        @Override
        protected Long doInBackground(String... url) {

            return ImageManager.DownloadFromUrl(url[0], "sovietic_dog.jpg");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();

    }

    public static Intent getDownloadService(final @NonNull Context callingClassContext, final @NonNull String downloadPath, final @NonNull String destinationPath) {
        return new Intent(callingClassContext, DownloadService.class)
                .putExtra(DOWNLOAD_PATH, downloadPath)
                .putExtra(DESTINATION_PATH, destinationPath);
    }
/*
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downloadPath = intent.getStringExtra(DOWNLOAD_PATH);
        String destinationPath = intent.getStringExtra(DESTINATION_PATH);
        startDownload(downloadPath, destinationPath);
    }*/

    private void startDownload(String downloadPath, String destinationPath) {
        Uri uri = Uri.parse(downloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);  // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);  // This will show notification on top when downloading the file.
        request.setTitle("Downloading a file"); // Title for notification.
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(destinationPath, uri.getLastPathSegment());  // Storage directory path
        ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request); // This will start downloading
    }
}
