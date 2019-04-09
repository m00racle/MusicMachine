package com.ideproject.mooracle.musicmachine;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 0:09
 * There's actually two ways we can run code on a separate thread.
 * We can pass in a runnable to a new thread object, which we're currently doing,
 * or we can extend the thread class and override its run method.
 * Let's refactor our code to instead use this second method
 * of extending the thread class.
 * Let's start by creating a new class called DownloadThread.
 * And let's make it extend the thread class.*/
public class DownloadThread extends Thread {
    private static final String TAG = DownloadThread.class.getSimpleName();

    //0:29
    //Then, back in our DownloadThread,
    //
    //0:32
    //let's declare a public DownloadHandler field named M handler
    //
    //0:45
    //mHandler.

    DownloadHandler mHandler;

    //0:42
    //Then let's override the run method of our new DownloadThread class by
    //hitting Ctrl+O to bring up the override dialog and then selecting the run method.
    //Then instead of calling super.run, let's call our downloadSong method instead.
    @Override
    public void run() {
        //0:47
        //Then, let's delete everything in the run method and
        //
        //0:51
        //type Looper.prepare,
        //
        //0:59
        //this creates a looper for a thread and also creates the message queue.
        //
        //1:05
        //Next, let's initialize our handler by typing mHandler
        //
        //1:10
        //equals new DownloadHandler.
        //
        //1:16
        //By default, a handler is associated with the looper for the current thread.
        //
        //1:20
        //Since we're in the run method of our DownloadThread, the current thread
        //
        //1:26
        //will be our DownloadThread, instead of the main thread, perfect!
        //1:31
        //And once our handler is created, the last step is to call
        //
        //1:36
        //Looper.loop to start looping over the message queue.

        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();
    }

}
