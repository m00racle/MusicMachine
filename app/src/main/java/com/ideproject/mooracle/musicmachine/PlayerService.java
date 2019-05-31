package com.ideproject.mooracle.musicmachine;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.*;
import android.support.annotation.Nullable;
import android.util.Log;

public class PlayerService extends Service {
    private static final String CHANNEL_ID = "ChannelMusic";

    //since we already separate process from Service and Activity we need to create Messenger to communicate
    //between them:
    //The Messenger will require us to add binder or handler we choose the later by instantiating PlayerHandler object
    //then use this to reference the handler to the instance of this class PlayerService

    public Messenger messenger = new Messenger(new PlayerHandler(this));

    private MediaPlayer mPlayer;
    private static final String TAG = PlayerService.class.getSimpleName();

    //initiate mPlayer only once which use the onCreate method:
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
        //create the notification channel
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //creating a notification
        //NOTE: since the Notification.Builder(context) is deprecated we need to change it to second form please refer
        //to the documentation about Notification.
        //Also this buider requires min SDK of 26 thus I need to change the gradle for app:

        Notification.Builder notificationBuilder = new Notification.Builder(this, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = notificationBuilder.build();


        //start the foreground
        startForeground(11, notification);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //: call stop self to stop all service and destroy once the task completed
                Log.d(TAG, "media player complete");
                //todo: bring back message to the activity to change the play button text to "Play"

                stopSelf();

                //after the player finished remove notification:
                stopForeground(true);
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

    //add new public method to create channel ID for the notification
    private void createNotificationChannel() {
        //this method should be called when this service is launched and safe to call it multiple times
        //this channelID is only used API 26+ and ignored by prior API
        //thus we need to make sure that the API is > 26 (oreo in here is represented as O)
        // as presented in the gradle app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           CharSequence name = "Music Machine";
           String description = "set notification preference for Music Machine app";
           int importance = NotificationManager.IMPORTANCE_LOW;

           //NOTE: IMPORTANCE_LOW is because I just want notification with visual and no audio when it first come

           //create new notification channel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            //creating the channel using the instance of Notif Manager from the system service
            //getSystemService can be called using class as argument thus in this case we use Notification Manager class
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
