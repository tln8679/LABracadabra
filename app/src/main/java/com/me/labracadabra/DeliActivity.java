package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.HashMap;

public class DeliActivity extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli);

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
        String toSpeak = "How many days a week should I eat yummy hamburgers and steak?";
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);

    }


    public void onPause() {
        super.onPause();
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }

    }

    public void onClick(View v) {
        // Move to next question
        String toSpeak;
        int count;
        String update;
        switch (v.getId()) {
            case R.id.once:
                // Wrong answer
                toSpeak = "incorrect!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                // Keeping track of # of wrong answers
                //score += 1;
                break;

            case R.id.thrice:
                // Wrong answer
                toSpeak = "incorrect!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                // Keeping track of # of wrong answers
//                score += 1;
                break;

            case R.id.twice:
                toSpeak = "Correct!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                Button nxt = (Button) findViewById(R.id.nxtbttn);
                nxt.setText("NEXT");
                // Keeping track of # of wrong answers
//                score += 1;
                break;

            case R.id.nxtbttn:
                toSpeak = "Correct!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                Intent intent = new Intent(DeliActivity.this, DeliActivityTwo.class);
                startActivity(intent);
                break;

            default:
                break;


        }
    }
}