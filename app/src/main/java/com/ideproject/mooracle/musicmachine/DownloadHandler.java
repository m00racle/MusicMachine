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

        //downloadSong(msg.obj.toString());
        // this was commented out since the method was moved to DownloadService class
    }




}
