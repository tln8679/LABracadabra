package com.me.labracadabra;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.HashMap;
import java.util.ArrayList;


/**
 * @author tln86
 * Created by Taylor Noble on 2/26/2018.
 * Filename: ProduceActivity.java
 * Purpose: This program file controls the fourth produce activity. It is a learning module for kids
 *          to learn about science in the prodcue aisle of a grocery store.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, better games could be created
 */
public class ProduceActivityFour extends AppCompatActivity {
    private TextToSpeech reader;
    private boolean a, d, e, k = false;
    private Button lc = null; // Stores last clicked button
    private int count = 0;
    private ArrayList<Button> choices = new ArrayList<>();
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    public final String CORRECT = "correct";
    public final String INCORRECT = "incorrect";


    /**
     * Created by Taylor Noble on 2/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_four);
        choices.add((Button) findViewById(R.id.vitamin_a));
        choices.add((Button) findViewById(R.id.vitamin_d));
        choices.add((Button) findViewById(R.id.vitamin_e));
        choices.add((Button) findViewById(R.id.vitamin_k));
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
        final String initMessage = "First tap a vitamin and then tap the body part that " +
                "it is associated with.";
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
     * Created by Taylor Noble on 2/26/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a button from the layout file is clicked
     * Data Structures: ints and strings
     * Possible revisions/extensions: none foreseen
     * Called: When an object is clicked (finger tap)
     */
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.vitamin_a:
                if (!a) {
                    lc = (Button) findViewById(R.id.vitamin_a);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }
                break;

            case R.id.vitamin_d:
                if(!d) {
                    lc = (Button) findViewById(R.id.vitamin_d);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }

                break;

            case R.id.vitamin_e:
                if(!e) {
                    lc = (Button) findViewById(R.id.vitamin_e);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }

                break;

            case R.id.vitamin_k:
                if(!k) {
                    lc = (Button) findViewById(R.id.vitamin_k);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }
                break;

            case R.id.eye:
                Button vita = (Button) findViewById(R.id.vitamin_a);
                Button eye = (Button) findViewById(R.id.eye);
                if (lc==vita && !a) {
                    sound(CORRECT);
                    vita.setBackgroundColor(Color.GREEN);
                    count += 1;
                    a = true;
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vita){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null) {
                    sound(INCORRECT);
                    lc.setBackgroundColor(Color.RED);
                    eye.animate().rotationBy(900);
                    ProduceActivity.incrementScore();
                }
                // When all 4 are correct, start next screen
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;

            case R.id.bone:
                Button vitd = (Button) findViewById(R.id.vitamin_d);
                Button bone = (Button) findViewById(R.id.bone);
                if (lc==vitd && !d) {
                    sound(CORRECT);
                    vitd.setBackgroundColor(Color.GREEN);
                    count += 1;
                    d = true;
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vitd){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null){
                    sound(INCORRECT);
                    lc.setBackgroundColor(Color.RED);
                    bone.animate().rotationBy(900);
                    ProduceActivity.incrementScore();
                }
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;

            case R.id.lungs:
                Button vite = (Button) findViewById(R.id.vitamin_e);
                Button lungs = (Button) findViewById(R.id.lungs);
                if (lc==vite && !e) {
                    sound(CORRECT);
                    vite.setBackgroundColor(Color.GREEN);
                    count += 1;
                    e = true;
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vite){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null){
                    sound(INCORRECT);
                    lc.setBackgroundColor(Color.RED);
                    lungs.animate().rotationBy(900);
                    ProduceActivity.incrementScore();
                }
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;

            case R.id.blood:
                Button vitk = (Button) findViewById(R.id.vitamin_k);
                Button blood = (Button) findViewById(R.id.blood);
                if (lc==vitk && !k) {
                    sound(CORRECT);
                    vitk.setBackgroundColor(Color.GREEN);
                    count += 1;
                    k = true;
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vitk){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null){
                    sound(INCORRECT);
                    lc.setBackgroundColor(Color.RED);
                    blood.animate().rotationBy(900);
                    ProduceActivity.incrementScore();
                }
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;
        }

    }
}
