package ca.uoit.dominick.a100517944_lab10;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.VideoView;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PlayMediaActivity extends AppCompatActivity {

    List<String> mediaArray;
    MediaPlayer gMp = new MediaPlayer();
    private int length;
    private int vLength;
    private int gMediaResource;
    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_media);

        mediaArray = Arrays.asList(getResources().getStringArray(R.array.items));

        ArrayAdapter<CharSequence> playerAdapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);

        Spinner sp = (Spinner) findViewById(R.id.media_item);
        sp.setAdapter(playerAdapter);
    }

    public void playItem(View view) {
        if(gMp.isPlaying()){
            gMp.stop();
        }
        String selectedMedia;

        Spinner sp = (Spinner) findViewById(R.id.media_item);
        selectedMedia = sp.getSelectedItem().toString();
        Uri uri = Uri.parse("android.resource://ca.uoit.dominick.a100517944_lab10/raw/" + selectedMedia);
        int mediaResource = getResources().getIdentifier(selectedMedia, "raw", "ca.uoit.dominick.a100517944_lab10");
        boolean alreadyPlaying = gMediaResource == mediaResource;
        Log.i("Lab10", alreadyPlaying + "");

        if(isVideoFile(uri)){
            if(alreadyPlaying){
                vv.seekTo(vLength);
                vv.start();
            }
            else {
                gMediaResource = mediaResource;
                vv = (VideoView) findViewById(R.id.videoView);
                vv.setVisibility(View.VISIBLE);
                vv.setVideoURI(uri);
                vv.start();
            }
        }
        else{
            if(vv != null){
                vv.setVisibility(View.INVISIBLE);
            }
            if(alreadyPlaying){
                gMp.seekTo(length);
                gMp.start();
            }
            else{
                gMediaResource = mediaResource;
                MediaPlayer mp = MediaPlayer.create(this, mediaResource);
                mp.start();
                gMp = mp;
            }
        }
    }

    public boolean isVideoFile(Uri uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(getApplication(),uri);
        String videoString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
        return videoString != null;
    }

    public void pauseItem(View view) {
        if(vv != null){
            vv.pause();
            vLength = vv.getCurrentPosition();
        }
        if(gMp != null) {
            gMp.pause();
            length=gMp.getCurrentPosition();
        }


    }
}
