package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author tln86
 * Created by Taylor Noble on 3/24/2018.
 * Filename: DairyActivity.java
 * Purpose: This program file controls the first dairy activity. It is a learning module for kids
 *      to learn about science in the prodcue aisle of a grocery store.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, a better list of modules could be made
 */
public class DairyActivityOne extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    private List visited = new ArrayList<ImageView>();
    protected int DOZEN = 12;
    // higher the number, means less stars
    private static int score = 0;
    public static int getScore() {
        return score;
    }
    public static void incrementScore(){score+=1;}

    /**
     * Created by Taylor Noble on 3/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_one);
        initReader();   //  Reader reads opening instructions
    }

    /**
     * Created by Taylor Noble on 3/24/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader(){
        final String initMessage = "One dozen equals 12. Crack a dozen eggs.";
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
     * Created by Taylor Noble on 3/24/2018.
     * Purpose: Calls the text readers speak method
     * Possible revision: Making a static string and passing it through the method may make this
     *          function more reusable
     */
    public void sound(String message) {
        reader.speak(message, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    /**
     * Created by Taylor Noble on 3/24/2018.
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
     * Created by Taylor Noble on 3/24/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a object from the layout file is clicked.
     *         Cracks eggs and decides when next screen is launched.
     * Data Structures: ints and strings. ArrayList to determine when all 11 have been clicked.
     * Possible revisions/extensions: none foreseen
     */
    public void onClick(View v){
        if(!visited.contains(v)){
            sound("crack");
            ProgressBar bar = findViewById(R.id.Bar);
            bar.incrementProgressBy(2);
            ImageView current = (ImageView) findViewById(v.getId());
            v.setBackgroundResource(R.drawable.ic_egg);
            if (v instanceof ImageView){ visited.add(v);}
        }

        if (visited.size() == DOZEN){
            Intent intent = new Intent(DairyActivityOne.this, DairyActivityTwo.class);
            startActivity(intent);
        }
    }

    /**
     * Created by Taylor Noble 4/22/2018
     * Purpose: User needs to complete the activity (designers choice)
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, DairyActivityOne.class);
        startActivity(intent);
    }
}