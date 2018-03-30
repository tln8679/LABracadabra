package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;


public class MagiciansActivity extends AppCompatActivity {
    public static String magican;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magicians);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.magic);
//        mp.start();
        reader=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
                    reader.setSpeechRate(.75f);

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
        String toSpeak = "Please, choose a magician!";
        // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mOne:
                // Loads the screen for the Locations
                // Needs to load the saved state for the first magician
                magican = "magician1";
                Intent locationsIntent = new Intent(this, Locations.class);
                startActivity(locationsIntent);
                break;

            case R.id.mTwo:
                // Loads the screen for the Locations
                // Needs to load the saved state for the second magician;
                magican = "magician2";
                Intent lIntent = new Intent(this, Locations.class);
                startActivity(lIntent);
                break;

            case R.id.mThree:
                // Loads the screen for the Locations
                // Needs to load the saved state for the third magician
                magican = "magician3";
                Intent lThreeIntent = new Intent(this, Locations.class);
                startActivity(lThreeIntent);
                break;
        }
    }

    public void onPause(){
        if(reader !=null){
            reader.stop();
            reader.shutdown();
        }
        super.onPause();
    }
}
