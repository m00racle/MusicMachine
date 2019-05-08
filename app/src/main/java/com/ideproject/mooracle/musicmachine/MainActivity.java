package com.ideproject.mooracle.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private PlayerService mPlayerService;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONG = "song";
    private boolean mBound = false; // this will track the connection is bound or not

    private Button mDownloadButton;
    private Button mPlayerButton;

    //build anonymous class for service connection:
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            PlayerService.LocalBinder mBinder = (PlayerService.LocalBinder) binder;
            mPlayerService = mBinder.getService();
            mBound = true;
            //check if the music is playing or not and set play button text accordingly:
            if (mPlayerService.isPlaying()){
                mPlayerButton.setText("pause");//todo: set this to string resource
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadButton = findViewById(R.id.downloadButton);
        mPlayerButton = findViewById(R.id.playerButton);

        //this was deleted since we use service now rather than thread handler to download the songs

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Inside the OnClick method, let's start with a toast that says downloading.
                //Make sure to pick the Create a new Toast option,
                //then hit Enter to move to the text parameter, and write Downloading.
                //Then, let's make a call to a new method called downloadSong.
                //And let's use alt + enter to create this method inside our MainActivity class.
                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();



                for (String song : Playlist.songs){
                   //this was deleted since we want to use service in DownloadService rather than handler
                    //to use service we must use intent just like we invoke activity
                    Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
                    intent.putExtra(KEY_SONG, song); //<- put song name as extra to be extracted later
                    //lastly start the service
                    startService(intent);
                }
            }
        });

        mPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if it was bound
                if (mBound){
                    //check if the song is playing?
                    if (mPlayerService.isPlaying()){
                        //pause the music and switch the button to pause
                        mPlayerService.pause();
                        mPlayerButton.setText("play");//todo: fix this to make string resource
                    } else {
                        mPlayerService.play();
                        mPlayerButton.setText("pause");//todo: fix this to make string resource
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound){
            unbindService(serviceConnection);
            mBound = false;
            //note: unbinding service will not change the mBound since onDisconnected only called in
            //extraordinary circumstances. Thus this class itself need to change mBound value.
        }
    }
}
