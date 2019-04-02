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

                //Inside the OnClick method, let's start with a toast that says downloading.
                //Make sure to pick the Create a new Toast option,
                //then hit Enter to move to the text parameter, and write Downloading.
                //Then, let's make a call to a new method called downloadSong.
                //And let's use alt + enter to create this method inside our MainActivity class.
                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

                //1:37
                //Lastly, back in MainActivity and our download button's onClick listener,
                //let's get rid of our runnable object,
                //And use our new DownloadThread class instead of the normal thread class.

                DownloadThread thread = new DownloadThread();

                //to differentiate this thread let's give it a name:
                thread.setName("DownloadThread");

                //lastly let's execute the thread using start() method:
                thread.start();
            }
        });
    }
}
