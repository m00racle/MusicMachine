package com.ideproject.mooracle.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

public class DownloadService extends Service {
    private static final String TAG = DownloadService.class.getSimpleName();
    private DownloadHandler mHandler;

    @Override
    public void onCreate() {
        //we create the download thread here:
        DownloadThread thread = new DownloadThread();
        thread.setName("downloadThread");
        thread.start();

        //then we need to create Handler for this thread:
        while (thread.mHandler == null){
            //this while loop is not a best practice but it was practical so far to give time for the mHandler to
            //form up before onCreate finished
            //NOTE: we use the thread(DownloadThread).mHandler object not this class (DownloadService) mHandler
            // because it will always be null and create infinite loop!!!!
            // mHandler in Download Thread class is not specified as public or private but I guess since we can access
            //it directly it was at least public
        }
        mHandler = thread.mHandler;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //get the intent extra from the MainActivity
        String song = intent.getStringExtra(MainActivity.KEY_SONG);

        //we sent back the downloadSong method back to handler so we can use it in download thread
        //now we just sent the song to the mHandler as message
        Message message = Message.obtain();
        message.obj = song;
        mHandler.sendMessage(message);

        //return the service to start redeliver intent
        return Service.START_REDELIVER_INTENT;

        //we need to declare this in the manifest!!
    }

   //downloadSong is sent back to handler


    @org.jetbrains.annotations.Nullable
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
