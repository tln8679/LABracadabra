package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 2/22/2018.
 * Filename: MagiciansActivity.java
 * Purpose: This program file let's the user choose from one of three saved states. The saved
 *          states keep track of the users progress and display scores for each module.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, a better list of modules could be made
 */
public class MagiciansActivity extends AppCompatActivity {
    private static String magician;
    public static String getMagician() {   //  Needed for the database
        return magician;
    }
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    private final int SPEECH_INIT_TIME = 400;

    /**
     * Created by Taylor Noble on 2/20/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magicians);
        initReader();
    }

    /**
     * Created by Taylor Noble on 4/3/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer. Could pass in the string
     *      for initMessage.
     */
    public void initReader(){
        final String initMessage = "Choose your magician";
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
     * Created by Taylor Noble on 2/22/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a object from the layout file is clicked.
     *         Decides which saved state is loaded for the user.
     * Data Structures: ints and strings
     * Possible revisions/extensions: none foreseen
     */
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mOne:
                // Loads the screen for the Locations
                // Needs to load the saved state for the first magician
                magician = "magician1";
                Intent locationsIntent = new Intent(this, Locations.class);
                startActivity(locationsIntent);
                break;

            case R.id.mTwo:
                // Loads the screen for the Locations
                // Needs to load the saved state for the second magician;
                magician = "magician2";
                Intent lIntent = new Intent(this, Locations.class);
                startActivity(lIntent);
                break;

            case R.id.mThree:
                // Loads the screen for the Locations
                // Needs to load the saved state for the third magician
                magician = "magician3";
                Intent lThreeIntent = new Intent(this, Locations.class);
                startActivity(lThreeIntent);
                break;
        }
    }

    @Override
    /**
     * Created by Taylor Noble on 4/6/2018.
     * Purpose: If the screen is paused (app minimized, user launches next screen via some input
     *          action, etc.), the reader needs to be killed.
     * Output:  None
     */
    public void onPause(){
        if(reader !=null){
            reader.stop();
            reader.shutdown();
        }
        super.onPause();
    }

    /**
     * Created by Taylor Noble 4/26/2018
     * Purpose: User needs to complete the activity (designers choice)
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MagiciansActivity.class);
        startActivity(intent);
    }
}