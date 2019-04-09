package com.ideproject.mooracle.musicmachine;

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

        final DownloadThread thread = new DownloadThread();

        //to differentiate this thread let's give it a name:
        thread.setName("DownloadThread");

        //lastly let's execute the thread using start() method:
        thread.start();

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Inside the OnClick method, let's start with a toast that says downloading.
                //Make sure to pick the Create a new Toast option,
                //then hit Enter to move to the text parameter, and write Downloading.
                //Then, let's make a call to a new method called downloadSong.
                //And let's use alt + enter to create this method inside our MainActivity class.
                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

               //3:33
                //We'll need to start our thread before we get to the OnClick method.
                //
                //3:38
                //Let's cut out our thread code and paste it above in the on create method.
                //3:55
                //We could create a new runnable for each song we want to download.
                //
                //3:59
                //But then we need to specify not only which song to download, but
                //
                //4:03
                //also how to download it like bringing your own recipe to the burrito truck.
                //
                //4:09
                //Wouldn't it be better if we could just give our handler a bunch of song titles
                //
                //4:13
                //and we could trust it to handle them all by itself?
                //4:19
                //Let's start by changing our comment to no longer include runnables.
                //
                //4:25
                //Then, let's add a for each loop to loop through the playlist.

                for (String song : Playlist.songs){
                    //4:40
                    //let's create a new message variable named message.
                    //
                    //4:52
                    //And let set it equal to Message.obtain.
                    //
                    //4:58
                    //We could create a new message object, but
                    //
                    //5:01
                    //since messages are used all over the operating system,
                    //
                    //5:05
                    //Android keeps a pool of message objects available for us to use.
                    //
                    //5:10
                    //This way, Android doesn't need to keep creating and
                    //
                    //5:13
                    //deleting a bunch of message objects.
                    //
                    //5:16
                    //Once a message has been handled, it just goes back to the pool to be re-used.

                    Message message = Message.obtain();
                    message.obj = song;
                    //5:31
                    //The OBJ property of a message lets us add any type of object we want to our message.
                    //
                    //5:39
                    //Since we want to tell our handler about a song to download,
                    //
                    //5:43
                    //we'll attach the song name to our message.

                    thread.mHandler.sendMessage(message);
                }
            }
        });
    }
}
