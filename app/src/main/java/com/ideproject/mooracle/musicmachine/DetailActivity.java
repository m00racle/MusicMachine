package com.ideproject.mooracle.musicmachine;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ideproject.mooracle.musicmachine.models.Song;

public class DetailActivity extends AppCompatActivity {
    public static final String SHARE_SONG = "com.mooracle.intent.action.SHARE_SONG";

    private RelativeLayout rootLayout;
    private Song song;
    private String songTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleLabel = findViewById(R.id.songTitleLabel);
        final CheckBox favoriteCheckbox = findViewById(R.id.checkBox);
        rootLayout = findViewById(R.id.rootLayout);

        Intent intent = getIntent();


        if (Intent.ACTION_SEND.equals(intent.getAction())) { //to avoid null pointer exception!!
            handleSendIntent(intent);
        }
        else {
            //        if ((songTitle = intent.getStringExtra(MainActivity.EXTRA_SONG)) != null){
//            titleLabel.setText(songTitle);
//        }

            if (intent.getParcelableExtra(MainActivity.EXTRA_SONG) != null){
                Song song = intent.getParcelableExtra(MainActivity.EXTRA_SONG);
                titleLabel.setText(song.getTitle());
                favoriteCheckbox.setChecked(song.isFavorite());
            }

            //get the list position of the song:
            final int listPosition = intent.getIntExtra(MainActivity.EXTRA_LIST_POSITION, 0);

            //handling the checkboxes
            favoriteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //send boolean isChecked:
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.EXTRA_FAVORITE, isChecked);
                    //send the list position back to main activity
                    resultIntent.putExtra(MainActivity.EXTRA_LIST_POSITION, listPosition);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //this already written in the beginning of GitHub prep:
        int itemId = item.getItemId();

        if (itemId == R.id.action_share) {
            // Share the song
            if (song != null) {
                Intent customIntent = new Intent(SHARE_SONG);
                customIntent.putExtra(MainActivity.EXTRA_SONG, song);
                Intent chooser = Intent.createChooser(customIntent, "Share Song");
                startActivity(chooser);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleSendIntent(Intent intent) {
        if (intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
            Snackbar.make(rootLayout, intent.getStringExtra(Intent.EXTRA_TEXT), Snackbar.LENGTH_LONG).show();
        }
    }
}
