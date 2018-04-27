package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 3/24/2018.
 * Filename: DeliActivity.java
 * Purpose: This program file controls the first deli activity. It is a learning module for kids to
 *          learn about science in the deli aisle of a grocery store.
 * Revised: 4/3/2018
 * Data Structures: Uses a hash map for the TextToSpeech API.
 * Reason for existence: Starts the deli learning module
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, better indirect learning games could be made
 */
public class DeliActivity extends AppCompatActivity {
    // higher the number, means less stars
    private static int score = 0;
    public static int getScore() {
        return score;
    }
    public static void incrementScore(){score+=1;}
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    public final String CORRECT = "correct";
    public final String INCORRECT = "incorrectMessage";

    @Override
    /**
     * Created by Taylor Noble on 3/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli);
        initReader();
    }

    /**
     * Created by Taylor Noble on 4/3/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     * Called: Called onCreate to initialise the reader
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader(){
        final String initMessage = "How many days a week should I eat yummy hamburgers and steak?";
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
     * Called: Called onCreate after text synthesizer is initialized
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
     * Purpose: This method defines what happens when a button from the layout file is clicked
     * Data Structures: ints and strings
     * Possible revisions/extensions: none foreseen
     */
    public void onClick(View v) {
        // Move to next question
        int count;
        String update;
        switch (v.getId()) {
            case R.id.once:
                // Wrong answer
                sound(INCORRECT);
                // Keeping track of # of wrong answers
                score += 1;
                break;

            case R.id.thrice:
                // Wrong answer
                sound(INCORRECT);
                // Keeping track of # of wrong answers
                score += 1;
                break;

            case R.id.twice:
                //  Right answer
                sound(CORRECT);
                Intent intent = new Intent(DeliActivity.this, DeliActivityTwo.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}