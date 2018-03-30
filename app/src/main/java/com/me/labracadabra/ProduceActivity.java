package com.me.labracadabra;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;


public class ProduceActivity extends AppCompatActivity {
    // higher the number, means less stars
    public static int score = 0;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_produce);
        // Instantiate audio
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a1);
//        mp.start();
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
        score += 1;
    }

    public void sound() {
        String toSpeak = "Which one is not like the other";
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
        String toSpeak;
        EditText editText;
        TextView w1;
        int count;
        String update;
        switch (v.getId()) {
            case R.id.banana:
                // Wrong answer
//                MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
//                mp.start();
                toSpeak = "incorrect!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);

                // Updating text to show incorrect number of responses
                editText = (EditText) findViewById(R.id.editText);
                w1 = (TextView) findViewById(R.id.wrong);
                w1.setText("INCORRECT");
                update = editText.getText().toString();
                count = Integer.parseInt(update) + 1;
                editText.setText(Integer.toString(count), TextView.BufferType.EDITABLE);

                // Keeping track of # of wrong answers
                score += 1;
                break;

            case R.id.orange:
                // Wrong answer
//                MediaPlayer mp2 = MediaPlayer.create(this, R.raw.wrong);
//                mp2.start();
                // Initializing reader
                toSpeak = "incorrect!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);

                // Updating text to show incorrect number of responses
                editText = (EditText) findViewById(R.id.editText);
                w1 = (TextView) findViewById(R.id.wrong);
                w1.setText("INCORRECT");
                update = editText.getText().toString();
                count = Integer.parseInt(update) + 1;
                editText.setText(Integer.toString(count), TextView.BufferType.EDITABLE);

                // Keeping track of # of wrong answers
                score += 1;
                break;

            case R.id.apples:
                // Wrong answer
//                MediaPlayer mp3 = MediaPlayer.create(this, R.raw.wrong);
//                mp3.start();
                // Initializing reader
                toSpeak = "incorrect!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);

                // Updating text to show incorrect number of responses
                editText = (EditText) findViewById(R.id.editText);
                w1 = (TextView) findViewById(R.id.wrong);
                w1.setText("INCORRECT");
                update = editText.getText().toString();
                count = Integer.parseInt(update) + 1;
                editText.setText(Integer.toString(count), TextView.BufferType.EDITABLE);

                // Keeping track of # of wrong answers
                score += 1;
                break;

            case R.id.chicken:
                // Move to next question
//                MediaPlayer mp4 = MediaPlayer.create(this, R.raw.correct);
//                mp4.start();
                // We don't want the audio to play over
                toSpeak = "Correct!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ProduceActivity.this, ProduceActivityTwo.class);
                        startActivity(intent);
                    }
                }, 700);
                break;

            default:
                break;
        }
    }
}
