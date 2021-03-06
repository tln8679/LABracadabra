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
 * Filename: BakeryActivityFour.java
 * Purpose: This program file controls the four bakery activity. It is a learning module for kids
 * to learn about science in the prodcue aisle of a grocery store.
 * Revised: N/a
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 * group of children, better games could be created
 */
public class BakeryActivityFour extends AppCompatActivity {
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    protected final int DELAY_TRANSITION = 700;   // TIme for reader before screen switch
    protected int CHANGE = 5;
    protected TextView minutes, hours;
    protected int mins, hrs;
    protected Button nxt;
    protected String res;

    /**
     * Created by Taylor Noble on 4/28/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_four);
        initReader();   //  Reader reads opening instructions
    }

    /**
     * Created by Taylor Noble on 4/28/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     * so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader() {
        final String initMessage = "Set the timer to 30 minutes";
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
     * Created by Taylor Noble on 4/28/2018.
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
     * Created by Taylor Noble on 4/28/2018.
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

            case R.id.add:
                minutes = findViewById(R.id.minutes);
                mins = Integer.parseInt(minutes.getText().toString());
                hours = findViewById(R.id.hours);
                hrs = Integer.parseInt(hours.getText().toString());
                if (mins < 5) {
                    res = "0" + String.valueOf(mins + CHANGE);
                    minutes.setText(res);
                }
                else if (mins == 55){
                    minutes.setText("00");
                    hrs += 1;
                    hours.setText(String.valueOf(hrs));
                }
                else {
                    minutes.setText(String.valueOf(mins + CHANGE));
                }
                mins += CHANGE;
                if (mins > 55){
                    mins = 0;
                }
                nxt = findViewById(R.id.nxt);
                if (mins == 30) {
                    Toast.makeText(BakeryActivityFour.this,
                            "Stop here!", Toast.LENGTH_SHORT)
                            .show();
                    sound("Now it's time to put the cake in the oven!");
                    nxt.setText("Next");
                } else {
                    // user needs to subtract
                    nxt.setText("");
                    sound(hours.getText().toString()+ " hours and" + minutes.getText().toString() + "minutes");
                }
                break;

            case R.id.sub:
                hours = findViewById(R.id.hours);
                hrs = Integer.parseInt(hours.getText().toString());
                minutes = findViewById(R.id.minutes);
                mins = Integer.parseInt(minutes.getText().toString());
                if (mins == 0 && hrs == 0) {
                    break;
                }
                // If we need to subtract 5 from a time with an hour we get x:55
                else if (mins == 0 && hrs > 0){
                    hours.setText(String.valueOf(hrs-1));
                    hrs -= 1;
                    minutes.setText("55");
                    mins = 55;
                }
                else if (mins < 10) {
                    res = "0" + String.valueOf(mins - CHANGE);
                    minutes.setText(res);
                    mins -= CHANGE;
                }
                else {
                    minutes.setText(String.valueOf(mins - CHANGE));
                    mins -= CHANGE;
                }
                nxt = findViewById(R.id.nxt);
                if (mins == 30) {
                    sound("Now it's time to put the cake in the oven!");

                    nxt.setText("Next");
                } else {
                    // user needs to subtract
                    nxt.setText("");
                    String showHours = hours.getText().toString();
                    int tmp = Integer.parseInt(showHours);
                    showHours = String.valueOf(tmp);
                    sound(showHours + " hours and"
                            + minutes.getText().toString() + "minutes");
                }
                break;

            case R.id.nxt:
                // Move to next question if clock is right
                if(nxt == null || nxt.getText().toString().equals("")){
                    break;
                }
                ProgressBar bar = findViewById(R.id.Bar);
                bar.incrementProgressBy(25);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(BakeryActivityFour.this,
                                BakeryActivityEnd.class);
                        startActivity(intent);
                    }
                }, DELAY_TRANSITION);
                break;

            default:
                break;
        }
    }


    /**
     * Created by Taylor Noble on 4/28/2018.
     * Purpose: Defines what happens when the phone back button is pressed
     * Output:  Resets the current screen
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BakeryActivityFour.class);
        startActivity(intent);
    }
}