package com.ideproject.mooracle.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class DownloadService extends Service {
    private static final String TAG = DownloadService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //get the intent extra from the MainActivity
        String song = intent.getStringExtra(MainActivity.KEY_SONG);

        //call the downloadSong method for each song
        downloadSong(song);
        //return the service to start redeliver intent
        return Service.START_REDELIVER_INTENT;

        //we need to declare this in the manifest!!
    }

    //we need to move the downloadSong method from DownloadHandler to this class since we want to use Service instead
    //of Handler Thread
    // Then we need to head to MainActivity to change the method for downloading each song from calling the handler to
    //calling the service
    private void downloadSong(String song) {

        long endTime = System.currentTimeMillis() + 10 * 1000;

        while (System.currentTimeMillis() < endTime){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Log.d(TAG, song + " Downloaded: ");
    }

    @org.jetbrains.annotations.Nullable
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
