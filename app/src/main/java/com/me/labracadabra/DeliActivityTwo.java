package com.me.labracadabra;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 3/24/2018.
 * Filename: DeliActivityTwo.java
 * Purpose: This program file controls the second deli activity. It is a learning module for kids to
 *          learn about science in the deli aisle of a grocery store.
 * Revised: 4/3/2018
 * Data Structures: Uses a hash map for the TextToSpeech API.
 * Reason for existence: Starts the deli learning module
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, better indirect learning games could be made
 */
public class DeliActivityTwo extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    private final int SPEECH_INIT_TIME = 400;

    @Override
    /**
     * Created by Taylor Noble on 3/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli_two);
        initReader();
    }

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
        ImageView muscle = (ImageView) findViewById(R.id.arm);
        muscle.animate().scaleXBy(2).scaleYBy(2);
    }
}
