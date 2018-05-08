package com.me.labracadabra;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 2/26/2018.
 * Filename: ProduceActivityThree.java
 * Purpose: This program file controls the third produce activity. It is a learning module for kids
 *          to learn about science in the prodcue aisle of a grocery store.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, better games could be created
 */
public class ProduceActivityThree extends AppCompatActivity {
    private TextToSpeech reader;
    // Keeping track to make sure they have clicked on each vitamin
    private boolean[] clicked = new boolean[4];
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_three);
        for(boolean b: clicked) {
            b = false;
        }
        initReader();
    }


    /**
     * Created by Taylor Noble on 4/3/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader(){
        final String initMessage = "Let's learn about vitamins! First we will start with fat soluble vitamins." +
                "Click on a vitamin to learn about it.";
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
     * Called: Called when priority is taken from the screen (new intent started, etc.)
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
     * Purpose: Check to see if the user has clicked and listened to all the information.
     *          We don't want the child to be able to hit next until they have learned.
     * Output:  Next button appears on screen. If next onClick new screen created.
     * Called: Called when a button is clicked.
     * Data structure: Loops through an array of booleans to check if the info has been clicked
     * @return True if all info has been clicked and false otherwise
     *
     */
    public Boolean checkStatus(){
        for(int i = 0; i < clicked.length; i++) {
            if (clicked[i] == false){
                return false;
            }
            else if (i == clicked.length-1 && clicked[i] == true){
                Button nxt = (Button) findViewById(R.id.next);
                nxt.setText("NEXT");
                return true;
            }
        }
        return false;
    }


    /**
     * Created by Taylor Noble on 2/26/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a button from the layout file is clicked
     * Data Structures: ints and strings
     * Possible revisions/extensions: none foreseen
     * Called: When an object is clicked (finger tap)
     */
    public void onClick(View v) {
        ProgressBar bar = findViewById(R.id.Bar);
        bar.incrementProgressBy(5);
        switch (v.getId()) {

            case R.id.vitamin_a:
                // Loads the screen for the fruit information
                String toSpeak = "Vitamin A is Good for your eyes! So eat your carrots and your " +
                        "fish!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vita = (TextView) findViewById(R.id.vitamin_a);
                vita.setTextColor(Color.RED);
                clicked[0] = true;
                checkStatus();
                break;

            case R.id.vitamin_d:
                toSpeak = "Vitamin D is Good for your bones! Drink your milk and eat your cheese!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vitd = (TextView) findViewById(R.id.vitamin_d);
                vitd.setTextColor(Color.RED);
                clicked[1] = true;
                checkStatus();
                break;

            case R.id.vitamin_e:
                // Loads the screen for the fruit information
                toSpeak = "Vitamin E is Good for your Lungs! Eat your avocados, nuts and seeds!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vite = (TextView) findViewById(R.id.vitamin_e);
                vite.setTextColor(Color.RED);
                clicked[2] = true;
                checkStatus();
                break;

            case R.id.vitamin_k:
                toSpeak = "Vitamin K is Good for your blood! Eat your spinach and cabbage!";
                reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
                TextView vitk = (TextView) findViewById(R.id.vitamin_k);
                vitk.setTextColor(Color.RED);
                clicked[3] = true;
                checkStatus();
                break;

            case R.id.next:
                for(int i = 0; i < clicked.length; i++) {
                    if (checkStatus()) {
                        Intent intent = new Intent(ProduceActivityThree.this, ProduceActivityFour.class);
                        startActivity(intent);
                    }
                }
            default: break;
        }
    }

    /**
     * Created by Taylor Noble 4/26/2018
     * Purpose: User needs to complete the activity (designers choice)
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, ProduceActivityThree.class);
        startActivity(intent);
    }
}