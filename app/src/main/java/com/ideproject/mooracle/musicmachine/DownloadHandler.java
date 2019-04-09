package com.ideproject.mooracle.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 0:10
 * Let's start by making a new class for our handler and
 *
 * 0:14
 * let's call it DownloadHandler And
 *
 * 0:22
 * let's make it extend the android.os.Handler class.*/

public class DownloadHandler extends Handler {
    private static final String TAG = DownloadHandler.class.getSimpleName();

    //6:16
    //The last thing we need to do,
    //
    //6:18
    //is tell our handler what it should do, when it gets a message.
    //
    //6:23
    //We can do this but over riding our handlers, handle message method.
    //
    //6:29
    //Let's go to the download handler class, and
    //
    //6:32
    //hit Ctrl+O to bring up the over ride dialog.


    @Override
    public void handleMessage(Message msg) {
        //7:15
        //instead of calling super.handlemessage.
        //
        //7:19
        //Let's call downloadSong, and
        //
        //7:23
        //pass in the song attached to our message,
        //
        //7:29
        //msg.obj.toString, to turn it into a string.

        downloadSong(msg.obj.toString());
    }

    //6:40
    //And once we've done that, all we need to do is call our download
    //
    //6:45
    //song method with the song name supplied by our message.
    //
    //6:51
    //Let's cut the download song method out of our DownloadThread and
    //
    //6:59
    //paste it into our DownloadHandler.
    //
    //7:06
    //Then let's quickly update the tag constant Alt+Enter,
    //
    //7:11
    //downloadhandler.class.get simple name and

    private void downloadSong(String song) {

        long endTime = System.currentTimeMillis() + 10 * 1000;

        while (System.currentTimeMillis() < endTime){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //7:37
        //Lastly, let's update the download song method to take in a song, String song.
        //
        //7:45
        //And let's change the log message to include the song name.

        Log.d(TAG, song + " Downloaded: ");
    }
}
