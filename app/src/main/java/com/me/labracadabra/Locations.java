package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Locale;


public class Locations extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.loc);
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
        String toSpeak = "What is your location? Are you at the grocery store, hardware store," +
                " library, park, automobile shop, or the doctors office?";
        // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    public void onPause(){
        if(reader !=null){
            reader.stop();
            reader.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.groceries:
                // Loads the screen for the Grocery store
                Intent groceryIntent = new Intent(this, GroceryActivity.class);
                startActivity(groceryIntent);
                break;

            case R.id.hardware:
                // Loads the screen for the hardware store
                break;

            case R.id.library:
                // Loads the screen for the library
                break;

            case R.id.park:
                // Loads the screen for the park
                break;
        }
    }
}
