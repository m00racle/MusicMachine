package com.ideproject.mooracle.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * This class is use to handle message communicate from and to the PlayerService through PlayerHandler
 * Thus it will process on how the looks in the UI based on the feedback from the PlayerService.*/

public class ActivityHandler extends Handler {
    //reference the handler to the activity class through constructor
    MainActivity mainActivity;

    public ActivityHandler(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    //Override the handleMassage method

    @Override
    public void handleMessage(Message msg) {
        //first we handle the responds from serviceMessenger message which asking isPlaying using arg1 = 2
        // to the PlayerHandler. That messenger will responds through replyTo channel which being set to
        //activityMessenger and will be handled here: (thus it was msg here is)
        if (msg.arg1 == 0){
            //music is not playing. If this is the first time connected then just make sure text of the button to play
            if (msg.arg2 == 1){
                //this means it is the first time connected just make sure the play button say "Play"
                //using the newly created setPlayerButtonText from MainActivity we can set it to "Play"
                mainActivity.setPlayerButtonText("Play");
            }else {
                //Play the music if this is not the first time onServiceConnected
                Message message = Message.obtain();
                message.arg1 = 0;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //Change the play button text to "pause
                mainActivity.setPlayerButtonText("Pause");
            }
        } else if (msg.arg1 == 1){
            //Music is playing. If this is the first time connected just make sure the play button text said "Pause"
            if (msg.arg2 == 1){
                //this means it re connected or first time connected, just make sure PlayButton says "Pause"
                mainActivity.setPlayerButtonText("Pause");
            }else {
                //if not Pause the music
                Message message = Message.obtain();
                message.arg1 = 1;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //Change the play button text to "Play"
                mainActivity.setPlayerButtonText("Play");
            }
        } else if (msg.arg1 == 3) {
            mainActivity.setPlayerButtonText("Play");
        }
    }
}
