package com.ideproject.mooracle.musicmachine;

import android.app.*;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.ideproject.mooracle.musicmachine.models.Song;

public class PlayerService extends Service {
    private static final String CHANNEL_ID = "ChannelMusic";
    private static final int REQUEST_OPEN = 99;

    //since we already separate process from Service and Activity we need to create Messenger to communicate
    //between them:
    //The Messenger will require us to add binder or handler we choose the later by instantiating PlayerHandler object
    //then use this to reference the handler to the instance of this class PlayerService

    public Messenger messenger = new Messenger(new PlayerHandler(this));
    public Messenger activityMessenger;

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
        //Also this builder requires min SDK of 26 thus I need to change the gradle for app:

        //get the intent for the title and artist
        String title ="";
        String artist ="";
        if (intent.getParcelableExtra(MainActivity.EXTRA_SONG) != null) {
            Song song = intent.getParcelableExtra(MainActivity.EXTRA_SONG);
            title = song.getTitle();
            artist = song.getArtist();
        }

        //building intent to activate the main intent when the notification was pressed:
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_OPEN, mainIntent, 0);

        Notification.Builder notificationBuilder = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_queue_music_white)
                .setContentTitle(title) //set the song title as notification title
                .setContentText(artist) //set the artist to text
                .setContentIntent(pendingIntent); //set pending intent to open main activity when notification pressed
        Notification notification = notificationBuilder.build();


        //start the foreground
        startForeground(11, notification);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //: call stop self to stop all service and destroy once the task completed
                stopSelf();

                //after the player finished remove notification:
                stopForeground(true);

                //send message back to Activity handler to inform that the song is done playing thus change the
                //mPlayerButton to "Play"
                Message message = Message.obtain();
                message.arg1 = 3;
                try {
                    activityMessenger.send(message);
                } catch (Exception e) {}
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
