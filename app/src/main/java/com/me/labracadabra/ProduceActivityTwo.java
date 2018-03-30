package com.me.labracadabra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Locale;

public class ProduceActivityTwo extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_two);

//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a2);
//        mp.start();
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                Intent intent = new Intent(ProduceActivityTwo.this, ProduceActivityThree.class);
//                startActivity(intent);
//            }
//        }, mp.getDuration());
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
        String toSpeak = "Why do my parents always tell me to eat my fruits and veggies!?";
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
        // Dramatic pause lol
        while (reader.isSpeaking()){

        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                reader.speak("Because they're packed with vitamins!", TextToSpeech.QUEUE_FLUSH, onlineSpeech);
            }
        }, 150);
    }


    public void onPause(){
        super.onPause();
        if(reader !=null){
            reader.stop();
            reader.shutdown();
        }

    }

    public void onClick(View v){
        // Move to next question
        Intent intent = new Intent(this, ProduceActivityThree.class);
        startActivity(intent);
    }
}
