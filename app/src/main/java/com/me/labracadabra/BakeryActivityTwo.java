package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 4/26/2018.
 * Filename: BakeryActivityTwo.java
 * Purpose: This program file controls the second bakery activity. It is a learning module for kids
 * to learn about science in the prodcue aisle of a grocery store.
 * Revised: N/a
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 * group of children, better games could be created
 */
public class BakeryActivityTwo extends AppCompatActivity {
    // higher the number, means less stars
    private static int score = 0;

    public static int getScore() {
        return score;
    }

    public static void incrementScore() {
        score += 1;
    }
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    protected final int DELAY_TRANSITION = 700;   // TIme for reader before screen switch
    public final String CORRECT = "correct";
    public final String INCORRECT = "incorrect";

    /**
     * Created by Taylor Noble on 4/26/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_two);
        initReader();   //  Reader reads opening instructions
    }

    /**
     * Created by Taylor Noble on 4/26/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     * so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader() {
        final String initMessage = "Which of the following is not found in a cake recipe";
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
     * Created by Taylor Noble on 4/26/2018.
     * Purpose: Calls the text readers speak method
     * Possible revision: Making a static string and passing it through the method may make this
     * function more reusable
     */
    public void sound(String message) {
        reader.speak(message, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    /**
     * Created by Taylor Noble on 4/26/2018.
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
     * Created by Taylor Noble on 4/26/2018.
     *
     * @param v: the content view (resource layout xml file)
     *           Purpose: This method defines what happens when a button from the layout file is clicked
     *           Data Structures: ints and strings
     *           Possible revisions/extensions: none foreseen
     *           Called: When an object is clicked (finger tap)
     */
    public void onClick(View v) {
        String toSpeak;
        switch (v.getId()) {
            case R.id.butter:
                // Wrong answer
                incorrectMessage();
                // Keeping track of # of wrong answers
                BakeryActivityOne.incrementScore();
                break;

            case R.id.chocolate:
                // Wrong answer
                incorrectMessage();
                // Keeping track of # of wrong answers
                BakeryActivityOne.incrementScore();
                break;

            case R.id.numdegrees:
                // Wrong answer
                // Initializing reader
                incorrectMessage();
                // Keeping track of # of wrong answers
                BakeryActivityOne.incrementScore();
                break;

            case R.id.add:
                // Wrong answer
                incorrectMessage();
                // Keeping track of # of wrong answers
                BakeryActivityOne.incrementScore();
                break;

            case R.id.chips:
                // Move to next question
                sound(CORRECT);
                ProgressBar bar = findViewById(R.id.Bar);
                bar.incrementProgressBy(25);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(BakeryActivityTwo.this, BakeryActivityThree.class);
                        startActivity(intent);
                    }
                }, DELAY_TRANSITION);
                break;

            default:
                break;
        }
    }

    /**
     * Created by Taylor Noble 04/26/2018
     * Purpose: To reduce duplicate code
     */
    public void incorrectMessage() {
        Toast.makeText(BakeryActivityTwo.this,
                "Incorrect!", Toast.LENGTH_SHORT)
                .show();
        sound(INCORRECT);
    }

    /**
     * Created by Taylor Noble 4/26/2018
     * Purpose: User needs to complete the activity (designers choice)
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BakeryActivityTwo.class);
        startActivity(intent);
    }
}