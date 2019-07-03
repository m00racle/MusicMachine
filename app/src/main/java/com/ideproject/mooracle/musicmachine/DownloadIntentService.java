package com.ideproject.mooracle.musicmachine;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import com.ideproject.mooracle.musicmachine.models.Song;

public class DownloadIntentService extends IntentService {
    private static final String TAG = DownloadIntentService.class.getSimpleName();
    private static final String DOWNLOAD_CHANNEL_ID = "Download Music";
    private static final int REQUEST_DETAIL = 88;
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 22;

    public DownloadIntentService() {
        super("DownloadIntentService");
        setIntentRedelivery(true);

    }

    private void createNotificationChannel() {
        //create notification channel for future notification from download intent service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Download Music";
            String description = "Set notification preference for Download Song Notification";
            int importance = NotificationManager.IMPORTANCE_LOW;

            //start build notification channel:
            NotificationChannel channel = new NotificationChannel(DOWNLOAD_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        createNotificationChannel();
        Song song = intent.getParcelableExtra(MainActivity.KEY_SONG);

        //TODO: BUG the notification cannot direct the detail activity to the right song detail.
        //make the notification clickable to detail page of the song:
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(MainActivity.EXTRA_SONG, song);
        detailIntent.putExtra(MainActivity.EXTRA_LIST_POSITION, song.getId());
        //the song position is on default value since we cannot access the playlist adapter directly
        PendingIntent pendingDetailIntent = PendingIntent.getActivity(this, REQUEST_DETAIL, detailIntent, 0);

        //build the notification for downloaded song:
        Notification.Builder builder = new Notification.Builder(this, DOWNLOAD_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_queue_music_white)
                .setContentTitle("Downloading")
                .setContentText(song.getTitle())
                .setProgress(0,0,true)
                .setContentIntent(pendingDetailIntent); //add click to detail of the song

        //use notification manager to load the recently built notification
        //notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }

        downloadSong(song.getTitle());
    }

    private void downloadSong(String song) {

        long endTime = System.currentTimeMillis() + 2 * 1000;

        while (System.currentTimeMillis() < endTime){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //each time each download finished always cancel the notification to ensure it disappear:
        notificationManager.cancel(NOTIFICATION_ID);

        Log.d(TAG, song + " Downloaded: ");
    }

}
