package com.ideproject.mooracle.musicmachine;

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

    //0:42
    //Then let's override the run method of our new DownloadThread class by
    //hitting Ctrl+O to bring up the override dialog and then selecting the run method.
    //Then instead of calling super.run, let's call our downloadSong method instead.
    @Override
    public void run() {
        //4:13
        //The last thing we need to do is call the downloadSong method for
        //each song in our playlist.
        for (String song : Playlist.songs) {
            //1:30
            //Next, let's call downloadSong instead of super.run.
            downloadSong();
        }
        //add debug message to indicate downloading process thread is finished:
        Log.d(TAG, "finish downloading all songs in the playlist");

        //4:41
        //Before we test this, let's take a minute to talk about what would happen if we
        //instead added our loop around the code and our download button's onClick method.
        //If we looped there, we would create a new thread for
        //each song, and all the songs would be downloading at the same time.
        //You'd think this would be great, but by trying to download all the songs at once,
        //we'd be giving our users a bad experience.
        //They want to listen to their playlist as soon as possible, and we've only got so
        //much bandwidth we can use for downloads.
    }

    //1:02
    //Let's cut the method out of MainActivity, And
    //paste it into the bottom of our DownloadThread class.
    private void downloadSong() {

        long endTime = System.currentTimeMillis() + 10 * 1000;

        while (System.currentTimeMillis() < endTime){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //6:55
        //Lastly, let's add a log message to the end of our download song method that says song downloaded.
        Log.d(TAG, "Song Downloaded: ");
    }
}
