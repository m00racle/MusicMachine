package com.ideproject.mooracle.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class PlayerService extends Service {

    private final IBinder binder = new LocalBinder();

    private MediaPlayer mPlayer;
    private static final String TAG = PlayerService.class.getSimpleName();

    //initiate mPlayer only once which use the onCreate method:
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
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

    public class LocalBinder extends Binder {
        //build a method to get service after binding:
        public PlayerService getService(){
            return PlayerService.this;
        }
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
