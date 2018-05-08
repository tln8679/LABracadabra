package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 2/24/2018.
 * Filename: GroceryActivity.java
 * Purpose: This program file controls the second dairy activity. It is a learning module for kids
 *          to learn about science in the prodcue aisle of a grocery store.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 * group of children, a better list of modules could be made
 */
public class DairyActivityTwo extends AppCompatActivity {
    protected int DISPLAY_TIME = 500;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    protected int count = 0;

    /**
     * Created by Taylor Noble on 2/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_two);
        initReader();   //  Reader reads opening instructions
    }

    /**
     * Created by Taylor Noble on 4/3/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     * so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader() {
        final String initMessage = "Milk comes from cows. Tap the cow until you have a " +
                "gallon of milk.";
        //  Creating a text2speech reader
        reader = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
                    reader.setSpeechRate(.75f);
                }
            }
        });
        //  Wait a little for the initialization to complete
        int SPEECH_INIT_TIME = 400;
        new Handler().postDelayed(new Runnable() {
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
     * function more reusable
     */
    public void sound(String message) {
        reader.speak(message, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    /**
     * Created by Taylor Noble on 4/6/2018.
     * Purpose: If the screen is paused (app minimized, user launches next screen via some input
     * action, etc.), the reader needs to be killed.
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
     * Created by Taylor Noble on 3/6/2018.
     *
     * @param v: the view (In this case the cow)
     *           Purpose: This method defines what happens when a object from the layout file is clicked.
     *           When the cow is clicked it will milk and moo
     *           Data Structures: Nothing interesting
     *           Possible revisions/extensions: none foreseen
     */
    public void onClick(View v) {
        final int EMPTY = 0;
        final ImageView drop = findViewById(R.id.drop);
        if (count < 5) {
            ProgressBar bar = findViewById(R.id.Bar);
            bar.incrementProgressBy(5);
            drop.setBackgroundResource(R.drawable.ic_drop);
            sound("mooooooooooooooooooooooooo");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                drop.setBackgroundResource(EMPTY);
            }
        }, DISPLAY_TIME);
        count += 1;
        if (count == 5) {
            sound("Wow that is a gallon of milk");
            int DELAY = 2000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DairyActivityTwo.this, DairyActivityThree.class);
                    startActivity(intent);
                }
            }, DELAY);
        }
        count += 1;
    }

    /**
     * Created by Taylor Noble 4/22/2018
     * Purpose: User needs to complete the activity (designers choice)
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DairyActivityTwo.class);
        startActivity(intent);
    }
}