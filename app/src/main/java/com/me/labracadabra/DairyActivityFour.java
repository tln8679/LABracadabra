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
 * Created by Taylor Noble on 2/24/2018.
 * Filename: GroceryActivity.java
 * Purpose: This program file controls the grocery screen. On the grocery view there is a table
 *          layout of aisles in the grocery store. When a aisle is selected that learning module
 *          starts.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, a better list of modules could be made
 */
public class DairyActivityFour extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    protected final int DELAY_TRANSITION = 700;   // Time for reader before screen switch
    public final String CORRECT = "correct";
    public final String INCORRECT = "incorrectMessage";

    /**
     * Created by Taylor Noble on 2/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_four);
        initReader();   //  Reader reads opening instructions
    }

    /**
     * Created by Taylor Noble on 4/3/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader(){
        final String initMessage = "Where do we get our dairy from?";
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
        //  Wait a little for the initialization to complete
        int SPEECH_INIT_TIME = 400;
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
     * Created by Taylor Noble on 2/26/2018.
     *
     * @param v: the content view (resource layout xml file)
     *           Purpose: This method defines what happens when an image from the layout file is clicked
     *           Data Structures: ints and strings
     *           Possible revisions/extensions: none foreseen
     *           Called: When an object is clicked (finger tap)
     */
    public void onClick(View v) {
        String toSpeak;
        switch (v.getId()) {
            case R.id.egg:
                // Wrong answer
                incorrect();
                // Keeping track of # of wrong answers
                DairyActivityOne.incrementScore();
                break;

            case R.id.chips:
                // Wrong answer
                // Initializing reader
                incorrect();
                // Keeping track of # of wrong answers
                DairyActivityOne.incrementScore();
                break;

            case R.id.butter:
                // Wrong answer
                incorrect();
                DairyActivityOne.incrementScore();
                break;

            case R.id.flour:
                // Move to next question
                sound("Moooooo" + CORRECT);
                ProgressBar bar = findViewById(R.id.Bar);
                bar.incrementProgressBy(25);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(DairyActivityFour.this, DairyActivityEnd.class);
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
    public void incorrect(){
        Toast.makeText(DairyActivityFour.this,
                "Incorrect!", Toast.LENGTH_SHORT)
                .show();
        sound(INCORRECT);
    }

    /**
     * Created by Taylor Noble 4/26/2018
     * Purpose: User needs to complete the activity (designers choice)
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, DairyActivityFour.class);
        startActivity(intent);
    }
}

