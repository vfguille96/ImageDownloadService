package com.vfguille.servicedownloadimage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


import android.util.Log;

public class ImageManager {

    private static final String PATH = "/data/data/com.vfguille.servicedownloadimage/";
    //private static final String PATH = "com.vfguille.servicedownloadimage_DownloadSongService_Download_path";

    public static long DownloadFromUrl(String imageURL, String fileName) {
        try {
            URL url = new URL(imageURL);
            File file = new File(PATH + fileName);

            long startTime = System.currentTimeMillis();
            Log.d("ImageManager", "download begining");
            Log.d("ImageManager", "download url:" + url);
            Log.d("ImageManager", "downloaded file name:" + fileName);
            /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();

            /*
             * Define InputStreams to read from the URLConnection.
             */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            /*
             * Read bytes to the Buffer until there is nothing more to read(-1).
             */
            //ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;

            /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);

            while ((current = bis.read()) != -1) {
                fos.write((byte)current);
            }
            fos.close();
            return ((System.currentTimeMillis() - startTime) / 1000);

        } catch (IOException e) {
            Log.d("ImageManager", "Error: " + e);
            return 0;
        }

    }
}