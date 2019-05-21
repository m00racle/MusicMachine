package com.ideproject.mooracle.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * This is the handler to handle message communicating between MainActivity and PlayerService
 * Since PlayerService is no longer in the same process as MainActivity thus it needs to use
 * messages to communicate the action taken such as play, pause and asking isPlaying. Thus,
 * this handler is how to handle from the PlayerService side.*/

public class PlayerHandler extends Handler {
    //Making reference to instances of PlayerService
    private PlayerService playerService;

    //we need to construct it to make a reference to exactly a PlayerService:
    public PlayerHandler(PlayerService playerService){
        this.playerService = playerService;
    }

    //now to handle the messages:

    @Override
    public void handleMessage(Message msg) {
        //we assume that the message from the activity will contain 0, 1, or 2
        //0 for command to play, 1 to pause and 2 to ask isPlaying
        switch (msg.arg1){
            case 0:
                playerService.play();
                break;
            case 1:
                playerService.pause();
                break;
            case 2:
                //this is rather tricky since we need to make a new message to reply to msg
                //we use if statement to form the arg1 (int) of the reply 1 means it is playing and 0 is not
                int isPlaying = playerService.isPlaying()? 1: 0;

                //create new message add the isPlaying as arg1 and replyTo msg
                Message message = Message.obtain();
                message.arg1 = isPlaying;
                if (msg.arg2 == 1){
                    //if the incoming message arg2 = 1 then return back 1 as arg2 through our message:
                    message.arg2 = 1;
                }
                message.replyTo = playerService.messenger;
                //note: this replyTo require the messenger field on the playerService instance to be public
                //this will set the reply directly from the message from activity thus make it easier for our
                //code to run.

                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
