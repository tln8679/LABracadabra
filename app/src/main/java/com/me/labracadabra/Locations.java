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
 * Created by Taylor Noble on 2/20/2018.
 * Filename: GroceryActivity.java
 * Purpose: This program file lets the user click on the location they are (grocery store, library,
 *          etc.) and starts the screen with those learning modules.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, a better list of modules could be made
 */
public class Locations extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    private Intent intent;


    /**
     * Created by Taylor Noble on 2/20/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
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
        final String initMessage = "Choose your location";
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
     * Created by Taylor Noble on 2/20/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a object from the layout file is clicked.
     *         Decides which learning place is loaded for the user.
     * Data Structures: ints and strings
     * Possible revisions/extensions: none foreseen
     */
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.groceries:
                // Loads the screen for the Grocery store
                Intent groceryIntent = new Intent(this, GroceryActivity.class);
                startActivity(groceryIntent);
                break;

            case R.id.hardware:
                intent = new Intent(this, UnderConstruction.class);
                startActivity(intent);
                break;

            case R.id.library:
                intent = new Intent(this, UnderConstruction.class);
                startActivity(intent);
                break;

            case R.id.park:
                intent = new Intent(this, UnderConstruction.class);
                startActivity(intent);
                break;

            case R.id.auto:
                intent = new Intent(this, UnderConstruction.class);
                startActivity(intent);
                break;

            case R.id.doctors:
                intent = new Intent(this, UnderConstruction.class);
                startActivity(intent);
                break;

            default:
                break;
        }
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