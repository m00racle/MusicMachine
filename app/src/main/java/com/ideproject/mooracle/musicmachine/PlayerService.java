package com.ideproject.mooracle.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

public class PlayerService extends Service {

    //since we already separate process from Service and Activity we need to create Messenger to communicate
    //between them:
    //The Messenger will require us to add binder or handler we choose the later by instantiating PlayerHandler object
    //then use this to reference the handler to the instance of this class PlayerService

    Messenger messenger = new Messenger(new PlayerHandler(this));

    private MediaPlayer mPlayer;
    private static final String TAG = PlayerService.class.getSimpleName();

    //initiate mPlayer only once which use the onCreate method:
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //: call stop self to stop all service and destroy once the task completed
                stopSelf();
            }
        });
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        mPlayer.release(); // this will stop the mPlayer
    }


    //we need to make two client methods one for playing and one for pausing
    public void play(){
        mPlayer.start();
    }

    public void pause(){
        mPlayer.pause();
    }

    //add new public method to check if the music is playing:
    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }
}
