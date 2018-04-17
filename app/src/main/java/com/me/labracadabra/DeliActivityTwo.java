package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
    protected Intent intent;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    private final String CORRECT = "Get them gains";
    private final String INCORRECT = "You are weak";
    protected int STATUS = 0;
    private int[] myImageList = new int[]{R.drawable.ic_steak, R.drawable.ic_chips,
            R.drawable.ic_tuna, R.drawable.ic_pizza};


    /**
     * Created by Taylor Noble on 3/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli_two);
        initReader();
    }

    public void initReader(){
        final String initMessage = "Tap the food that will make your muscles bigger";
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
     * Purpose: Switch the images to two different foods every time the user gets a question correct
     *          action, etc.), the reader needs to be killed.
     * Output:  Changed images
     */
    public void changeImages(int count) {
        ImageButton protein = findViewById(R.id.choice1);
        ImageButton junk = findViewById(R.id.choice2);
        protein.setBackgroundResource(myImageList[count]);
        junk.setBackgroundResource(myImageList[count+1]);
    }

    /**
     * Created by Taylor Noble on 4/6/2018.
     * @param v: the content view (resource layout xml file)
     */
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.choice1:
                if (STATUS == 4){
                    Intent intent = new Intent(DeliActivityTwo.this, DeliActivityThree.class);
                    startActivity(intent);
                    break;
                }
                // Correct
                sound(CORRECT);
                // change images
                changeImages(STATUS);
                // grow arms
                ImageView muscle = (ImageView) findViewById(R.id.arm);
                muscle.animate().scaleYBy(1.35f).scaleXBy(1.35f);
                // update progress bar
                ProgressBar bar = findViewById(R.id.Bar);
                bar.incrementProgressBy(8);
                STATUS += 2;
                break;

            case R.id.choice2:
                // Incorrect
                sound(INCORRECT);
                DeliActivity.incrementScore();
                break;


            default:
                break;
        }

    }
}
