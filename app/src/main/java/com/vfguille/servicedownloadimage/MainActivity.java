package com.vfguille.servicedownloadimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private static final String IMAGE_DOWNLOAD_PATH = "http://iruntheinternet.com/lulzdump/images/russian-dog-doge-communist-russia-1421240491F.jpg";
    private static final String IMAGE_DOWNLOAD_PATH = "https://services.meteored.com/img/article/hoy-en-el-dia-mundial-del-campo-analizamos-la-influencia-del-camblio-climatico-6922-4_768.jpg";
    private static final String SONG_DOWNLOAD_PATH = "http://i3.songcloud.cc/download/372078866c121010322f5808315818f6fe33e8e2/ZEF4NCIfbR8/8a09d4bc5eef8cc6.mp3?s=musicaq.net";
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.downloadImageButton).setOnClickListener(this);
        findViewById(R.id.downloadSongButton).setOnClickListener(this);
        intent = new Intent(MainActivity.this, DownloadService.class);
        //PEDIR PERMISOS DESPUES DE CREAR SERVICIO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return;
        }
        DirectoryHelper.createDirectory(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloadImageButton: {
                //intent = new Intent(MainActivity.this, DownloadService.class);
                //startService(DownloadService.getDownloadService(this, IMAGE_DOWNLOAD_PATH, DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                intent.putExtra("urlPath", IMAGE_DOWNLOAD_PATH);
                startService(intent);
                break;
            }
            case R.id.downloadSongButton: {
                startService(DownloadService.getDownloadService(this, SONG_DOWNLOAD_PATH, DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                DirectoryHelper.createDirectory(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
