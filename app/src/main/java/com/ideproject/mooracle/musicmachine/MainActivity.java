package com.ideproject.mooracle.musicmachine;

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
    //1:53
    //Great, let's switch back to our activity and
    //declare our button as a field at the top of our class.
    //Let's call it mDownloadButton.
    private Button mDownloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadButton = findViewById(R.id.downloadButton);

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2:57
                //Inside the OnClick method, let's start with a toast that says downloading.
                //Make sure to pick the Create a new Toast option,
                //then hit Enter to move to the text parameter, and write Downloading.
                //Then, let's make a call to a new method called downloadSong.
                //And let's use alt + enter to create this method inside our MainActivity class.
                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                donwloadSong();
            }
        });
    }

    private void donwloadSong() {
        //3:36
        //Inside the download song method, ideally, we would download a song, but since downloading can be tricky and
        // isn't the point of this course, we'll just pretend to download. So instead of downloading a song,
        // let's just have this method wait ten seconds to simulate about how long it might take to download a song.
        // An easy way to do this is to add ten seconds to the current time and then run a while loop
        // until our ten seconds are up.
        long endTime = System.currentTimeMillis() + 10 * 1000;

        while (System.currentTimeMillis() < endTime){
            //6:05
            //Right now it's checking the current time against the end time thousands of times a second and that's a
            // huge waste of processing power. Instead, inside the body of our while loop, let's wait one second before
            // looping. We can do this by using the Thread.sleep method. And if we pass on a parameter, that's how many
            // milliseconds we'll wait, or sleep, before continuing.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //6:55
        //Lastly, let's add a log message to the end of our download song method that says song downloaded.
        Log.d(TAG, "Song Downloaded: ");
    }
}
