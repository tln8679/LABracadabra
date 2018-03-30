package com.me.labracadabra;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class ProduceActivityThree extends AppCompatActivity {
    private TextToSpeech reader;
    // Keeping track to make sure they have clicked on each vitamin
    private boolean[] clicked = new boolean[4];
    private HashMap<String, String> onlineSpeech = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_three);
        for(boolean b: clicked) {
            b = false;
        }
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a3);
//        mp.start();
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                Button next = (Button)findViewById(R.id.next);
//                next.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        // Perform action on click
//                        Intent intent = new Intent(ProduceActivityThree.this, ProduceActivityFour.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//        }, mp.getDuration());

        reader = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
                    reader.setSpeechRate(.75f);

                }
            }
        });
        // wait a little for the initialization to complete
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sound();
            }
        }, 400);
    }

    public void sound() {
        String toSpeak = "Let's learn about vitamins! First we will start with fat soluble vitamins." +
                "Click on a vitamin to learn about it.";
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    public void onPause() {
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.vitamin_a:
                // Loads the screen for the fruit information
                String toSpeak = "Vitamin A is Good for your eyes! So eat your carrots and your " +
                        "fish!";
                // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vita = (TextView) findViewById(R.id.vitamin_a);
                vita.setTextColor(Color.RED);
                clicked[0] = true;
                break;

            case R.id.vitamin_d:
                toSpeak = "Vitamin D is Good for your bones! Drink your milk and eat your cheese!";
                // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vitd = (TextView) findViewById(R.id.vitamin_d);
                vitd.setTextColor(Color.RED);
                clicked[1] = true;
                break;

            case R.id.vitamin_e:
                // Loads the screen for the fruit information
                toSpeak = "Vitamin E is Good for your Lungs! Eat your avocados, nuts and seeds!";
                // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vite = (TextView) findViewById(R.id.vitamin_e);
                vite.setTextColor(Color.RED);
                clicked[2] = true;
                break;

            case R.id.vitamin_k:
                toSpeak = "Vitamin K is Good for your blood! Eat your spinach and cabbage!";
                // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vitk = (TextView) findViewById(R.id.vitamin_k);
                vitk.setTextColor(Color.RED);
                clicked[3] = true;
                break;

            case R.id.next:
                for(int i = 0; i < clicked.length; i++) {
                    if (clicked[i] == false){break;}
                    else if (i == clicked.length-1 && clicked[i] == true){
                        Intent intent = new Intent(ProduceActivityThree.this, ProduceActivityFour.class);
                        startActivity(intent);
                    }
                }
            default: break;
        }
    }
}
