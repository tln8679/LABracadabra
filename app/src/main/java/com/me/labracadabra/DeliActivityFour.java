package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 3/24/2018.
 * Filename: DeliActivityFour.java
 * Purpose: This program file controls the fourth deli activity. It is a learning module for kids to
 *          learn about science in the deli aisle of a grocery store.
 * Revised: 4/3/2018
 * Data Structures: Uses a hash map for the TextToSpeech API.
 * Reason for existence: Starts the deli learning module
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, better indirect learning games could be made
 */
public class DeliActivityFour extends AppCompatActivity {
    protected Intent intent;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    protected int STATUS = 0;


    /**
     * Created by Taylor Noble on 3/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli_four);
        initReader();
    }

    public void initReader(){
        final String initMessage = "Tap the plus sign until you have one pound";
        //  Creating a text2speech reader
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
                sound(initMessage);
            }
        }, SPEECH_INIT_TIME);
    }


    /**
     * Created by Taylor Noble on 4/7/2018.
     * Purpose: Calls the text readers speak method
     * Possible revision: Making a static string and passing it through the method may make this
     *          function more reusable
     */
    public void sound(String message) {
        reader.speak(message, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }


    /**
     * Created by Taylor Noble on 4/6/2018.
     * Purpose: If the screen is paused (app minimized, user launches next screen via some input
     *          action, etc.), the reader needs to be killed.
     * Output:  None
     */
    public void onPause() {
        super.onPause();
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
    }


    /**
     * Created by Taylor Noble on 4/6/2018.
     * @param v: the content view (resource layout xml file)
     */
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.plus:
                // Correct
                String CORRECT = "Good";
                sound(CORRECT);
                // grow arms
                ImageView chick = (ImageView) findViewById(R.id.chix);
                chick.animate().scaleYBy(1.25f).scaleXBy(1.25f);
                // set ounces
                TextView num = (TextView) findViewById(R.id.num);
                STATUS += 2;
                num.setText(String.valueOf(STATUS));
                // update progress bar
                ProgressBar bar = findViewById(R.id.Bar);
                bar.incrementProgressBy(4);
                if (STATUS == 16){
                    Intent intent = new Intent(DeliActivityFour.this, DeliActivityEnd.class);
                    startActivity(intent);
                    break;
                }
                break;

            default:
                break;
        }

    }
}
