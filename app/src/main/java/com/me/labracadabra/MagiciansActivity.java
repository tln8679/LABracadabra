package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;


public class MagiciansActivity extends AppCompatActivity {
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magicians);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.magic);
//        mp.start();
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                    t1.setPitch(.75f);
                }
            }
        });
        // wait a little for the initialization to complete
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                sound();
            }
        }, 400);


    }

    public void sound(){
        String toSpeak = "Please, choose a location!";
        // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mOne:
                // Loads the screen for the Locations
                // Needs to load the saved state for the first magician
                Intent locationsIntent = new Intent(this, Locations.class);
                startActivity(locationsIntent);
                break;

            case R.id.mTwo:
                // Loads the screen for the Locations
                // Needs to load the saved state for the second magician;
                Intent lIntent = new Intent(this, Locations.class);
                startActivity(lIntent);
                break;

            case R.id.mThree:
                // Loads the screen for the Locations
                // Needs to load the saved state for the third magician
                Intent lThreeIntent = new Intent(this, Locations.class);
                startActivity(lThreeIntent);
                break;
        }
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
