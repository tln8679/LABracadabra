package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * @author tln86
 * Created by Taylor Noble on 4/26/2018.
 * Filename: BakeryActivityThree.java
 * Purpose: This program file controls the third bakery activity. It is a learning module for kids
 * to learn about science in the prodcue aisle of a grocery store.
 * Revised: N/a
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 * group of children, better games could be created
 */
public class BakeryActivityThree extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    protected final int DELAY_TRANSITION = 700;   // TIme for reader before screen switch
    protected int CHANGE = 50;
    protected TextView temp;
    protected int deg;
    protected Button nxt;

    /**
     * Created by Taylor Noble on 2/26/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_three);
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
        final String initMessage = "Set the oven to three hundred and fifty degrees";
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
     * Created by Taylor Noble on 2/26/2018.
     *
     * @param v: the content view (resource layout xml file)
     *           Purpose: This method defines what happens when a button from the layout file is clicked
     *           Data Structures: ints and strings
     *           Possible revisions/extensions: none foreseen
     *           Called: When an object is clicked (finger tap)
     */
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add:
                temp = findViewById(R.id.numdegrees);
                deg = Integer.parseInt(temp.getText().toString());
                temp.setText(String.valueOf(deg + CHANGE));
                deg += CHANGE;
                nxt = findViewById(R.id.nxt);
                if (deg == 350) {
                    Toast.makeText(BakeryActivityThree.this,
                            "Stop here!", Toast.LENGTH_SHORT)
                            .show();
                    sound("Now it's time to put the cake in the oven!");
                    nxt.setText("Next");
                }
                else {
                    // user needs to subtract
                    nxt.setText("");
                    sound(temp.getText().toString());
                }
                break;

            case R.id.sub:
                temp = findViewById(R.id.numdegrees);
                deg = Integer.parseInt(temp.getText().toString());
                if (deg == 0) {
                    break;
                }
                temp.setText(String.valueOf(deg - CHANGE));
                deg -= CHANGE;
                nxt = findViewById(R.id.nxt);
                if (deg == 350) {
                    nxt.setText("Next");
                }
                else {
                    // user needs to subtract
                    nxt.setText("");
                    sound(temp.getText().toString());
                }
                break;

            case R.id.nxt:
                // Move to next question if temperature is set
                if(nxt == null || nxt.getText().toString().equals("")){
                    break;
                }
                ProgressBar bar = findViewById(R.id.Bar);
                bar.incrementProgressBy(25);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(BakeryActivityThree.this,
                                BakeryActivityFour.class);
                        startActivity(intent);
                    }
                }, DELAY_TRANSITION);
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
    public void onBackPressed() {
        Intent intent = new Intent(this, BakeryActivityThree.class);
        startActivity(intent);
    }
}