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
 * Created by Taylor Noble on 2/26/2018.
 * Filename: ProduceActivityTwo.java
 * Purpose: This program file controls the second produce activity. It is a learning module for kids
 *          to learn about science in the prodcue aisle of a grocery store.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, better games could be created
 */
public class ProduceActivityTwo extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_two);
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
        reader=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
                    reader.setSpeechRate(.75f);
                }
            }
        });
        // Delay for text reader initialization to complete
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                sound();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    // I don't want the next button to appear too early
                    public void run() {
                        Button next = (Button)findViewById(R.id.nxtbttn);
                        next.setText("NEXT");
                    }
                }, SPEECH_INIT_TIME);
            }
        }, SPEECH_INIT_TIME);
    }

    /**
     * Created by Taylor Noble on 4/7/2018.
     * Purpose: Calls the text readers speak method
     * Possible revision: Making a static string and passing it through the method may make this
     *          function more reusable
     *  Called: Called onCreate after text synthesizer is initialized
     */
    public void sound(){
        String toSpeak = "Why do my parents always tell me to eat my fruits and veggies!?";
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
        // Dramatic pause lol
        while (reader.isSpeaking()){

        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                reader.speak("Because they're packed with vitamins!", TextToSpeech.QUEUE_FLUSH, onlineSpeech);
            }
        }, 150);

    }

    /**
     * Created by Taylor Noble on 4/6/2018.
     * Purpose: If the screen is paused (app minimized, user launches next screen via some input
     *          action, etc.), the reader needs to be killed.
     * Output:  None
     * Called: Called when priority is taken from the screen (new intent started, etc.)
     */
    public void onPause(){
        super.onPause();
        if(reader !=null){
            reader.stop();
            reader.shutdown();
        }

    }
    /**
     * Created by Taylor Noble on 2/26/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a button from the layout file is clicked
     * Data Structures: ints and strings
     * Possible revisions/extensions: none foreseen
     * Called: When an object is clicked (finger tap)
     */
    public void onClick(View v){
        // Move to next question
        Intent intent = new Intent(this, ProduceActivityThree.class);
        startActivity(intent);
    }
}
