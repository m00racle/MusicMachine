package com.ideproject.mooracle.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //7:18
    //Alt+Enter to importLog and then let's use Alt+Enter to create our TAG constant.
    // And let's set it equal to MainActivity.class.getSimpleName.
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONG = "song";
    private boolean mBound = false; // this will track the connection is bound or not
    //1:53
    //Great, let's switch back to our activity and
    //declare our button as a field at the top of our class.
    //Let's call it mDownloadButton.
    private Button mDownloadButton;
    private Button mPlayerButton;

    //build anonymous class for service connection:
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
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
