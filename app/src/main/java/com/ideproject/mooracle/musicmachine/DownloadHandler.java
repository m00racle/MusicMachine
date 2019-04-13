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
    private DownloadService service;

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
        //2:13
        //Over and download handler and side the handle message
        //
        //2:18
        //method is the call to download song.
        //
        //2:25
        //Once this call is finished, our song has been downloaded.
        //
        //2:30
        //Let's add a line after this call and stop our service.

        downloadSong(msg.obj.toString());

        //2:48
        //Calling stop service, stops the service entirely.
        //
        //2:53
        //If we called stop service after only the first song,
        //
        //2:57
        //we'd be stopping the service before it finished working.
        //
        //3:00
        //Not good.
        //
        //3:02
        //Instead, there's a version of the stop self method that takes an integer When
        //
        //3:07
        //onStartCommand is called, one of the parameters is an integer named startID.
        //
        //3:16
        //Calling stops self and passing in this startID makes sure that we don't
        //
        //3:21
        //stop our service until it has handled all of the startIDs.
        //
        //3:26
        //Okay, so we need to get the startID from onStartCommand and pass it to our handler.
        //
        //3:33
        //Then, when downloading is finished,
        //
        //3:36
        //we need to use the correctonStartCommand and call stopSelf on our service.

        service.stopSelf(msg.arg1);
        //5:09
        //The last thing we need to do,
        //
        //5:10
        //is use mService to call stop self at the bottom of handle message.
        //
        //5:15
        //Let's type mService.stopSelf and
        //
        //5:20
        //pass in msg.arg1 to make sure that we only
        //
        //5:25
        //stop the service if it's handled all of our startIDs.

    }

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

    public void setService(DownloadService service) {
        //4:58
        //Looks like we've got a field for our service named mService.
        //
        //5:04
        //And we've got a new method called, setService as well.
        this.service = service;
    }
}
