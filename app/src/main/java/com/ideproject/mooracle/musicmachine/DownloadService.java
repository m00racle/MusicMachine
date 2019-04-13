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

        //4:10
        //Now, we just need to call stop self on our service when our handler's done working.
        //
        //4:17
        //An easy way to do this would be to just pass a reference to our service to
        //
        //4:22
        //our handler.
        //
        //4:23
        //And the bottom of the OnCreate Let's type
        //
        //4:29
        //mhandler.setService.
        //
        //4:35
        //And pass in the keyword this to pass in our service.
        //
        //4:40
        //Then let's use Alt Enter to create a setter.

        mHandler.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //get the intent extra from the MainActivity
        String song = intent.getStringExtra(MainActivity.KEY_SONG);

        //we sent back the downloadSong method back to handler so we can use it in download thread
        //now we just sent the song to the mHandler as message
        Message message = Message.obtain();
        message.obj = song;

        //3:42
        //Back in download service, right above where we send our message,
        //
        //3:47
        //let's attach the startID to our message using one of the argument properties.
        //
        //3:53
        //Each message object has two integer properties named arg1 and
        //
        //3:59
        //arg2, which we can use for passing arguments.
        //
        //4:04
        //Let's set arg1 Equal to startId.
        message.arg1 = startId;


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
