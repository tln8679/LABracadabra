package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.*;

/**
 * @author tln86
 * Created by Taylor Noble on 2/24/2018.
 * Filename: GroceryActivity.java
 * Purpose: This program file controls the grocery screen. On the grocery view there is a table
 *          layout of aisles in the grocery store. When a aisle is selected that learning module
 *          starts.
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, a better list of modules could be made
 */
public class GroceryActivity extends AppCompatActivity {
    protected Intent intent;
    private static String activity;
    public static String getActivity() {   //   Needed for database
        return activity;
    }
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    private dbManager dbHelper;



    /**
     * Created by Taylor Noble on 2/24/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        initReader();   //  Reader reads opening instructions
        dbHelper = new dbManager(this);
        loadScores();   //  Load the stars for best scores
    }

    /**
     * Created by Taylor Noble on 4/3/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader(){
        final String initMessage = "Find where you are in the store and tap it.";
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

    /**
     * Created by Taylor Noble on 3/18/2018.
     * Purpose: Loads the stars the user has made on each module and displays them
     * Data Structures: ArrayList of scores
     * Possible revision: A better scoring algorithm could be made. Stars have to be set for each
     *       learning module. Probable be better to loop through a list of the modules. This
     *       would require mapping each ImageView to its activity. Would be harder to implement
     *       and may be more expensive
     */
    private void loadScores() {
        int GOOD = 3;   // these following numbers represent number of incorrect clicks
        int NOT_PLAYED = 0;
        int OKAY = 8;
        int BAD = 20;



        // Produce progress
        //   Get player's stars from db to display
        ArrayList<String> scores = dbHelper.getBestScore(MagiciansActivity.getMagician(), "produce");
        String bestProduceScore;
        if (scores.size()>0) {
            bestProduceScore = scores.get(0);
        } else {
            bestProduceScore = "0";    //  If not played yet, there are 0 clicks. Display 0 stars.
        }

        ImageView ps1 = (ImageView) findViewById(R.id.ps1);
        ImageView ps2 = (ImageView) findViewById(R.id.ps2);
        ImageView ps3 = (ImageView) findViewById(R.id.ps3);
        if (Integer.parseInt(bestProduceScore) < GOOD && Integer.parseInt(bestProduceScore) != NOT_PLAYED) {
            ps1.setBackgroundResource(R.drawable.ic_star);
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (Integer.parseInt(bestProduceScore) >= GOOD && Integer.parseInt(bestProduceScore) < OKAY) {
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (Integer.parseInt(bestProduceScore) < BAD && Integer.parseInt(bestProduceScore) > OKAY) {
            ps3.setBackgroundResource(R.drawable.ic_star);
        }

        // Deli progress
        //   Get player's stars from db to display
        scores = dbHelper.getBestScore(MagiciansActivity.getMagician(), "produce");
        String bestDeliScore;
        if (scores.size()>0) {
            bestDeliScore = scores.get(0);
        } else {
            bestDeliScore = "0";    //  If not played yet, there are 0 clicks. Display 0 stars.
        }
        ImageView ds1 = (ImageView) findViewById(R.id.dels1);
        ImageView ds2 = (ImageView) findViewById(R.id.dels2);
        ImageView ds3 = (ImageView) findViewById(R.id.dels3);
        if (Integer.parseInt(bestDeliScore) < GOOD && Integer.parseInt(bestDeliScore) != NOT_PLAYED) {
            ps1.setBackgroundResource(R.drawable.ic_star);
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (Integer.parseInt(bestDeliScore) >= GOOD && Integer.parseInt(bestDeliScore) < OKAY) {
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (Integer.parseInt(bestDeliScore) < BAD && Integer.parseInt(bestDeliScore) > OKAY) {
            ps3.setBackgroundResource(R.drawable.ic_star);
        }
    }

    /**
     * Created by Taylor Noble on 4/6/2018.
     * Purpose: If the screen is paused (app minimized, user launches next screen via some input
     *          action, etc.), the reader needs to be killed.
     * Output:  None
     */
    @Override
    public void onPause() {
        super.onPause();
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
    }

    /**
     * Created by Taylor Noble on 4/6/2018.
     * Purpose: If the app is killed, the reader needs to be killed.
     * Output:  None
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
    }

    /**
     * Created by Taylor Noble on 3/6/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a object from the layout file is clicked.
     *         Decides which module is loaded for the user.
     * Data Structures: ints and strings
     * Possible revisions/extensions: none foreseen
     */
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.produce:
                // Loads the screen for the fruit information
                activity = "produce";
                intent = new Intent(this, ProduceActivity.class);
                startActivity(intent);
                break;

            case R.id.deli:
                // Loads the screen for the fruit information
                activity = "deli";
                intent = new Intent(this, DeliActivity.class);
                startActivity(intent);
                break;
//
//            case R.id.dairy:
//             // Loads the screen for the protein information
//             Intent proIntent = new Intent(this, ProteinActivity.class);
//             startActivity(proIntent);
//             break;
//
//             case R.id.bakery:
//             // Loads the screen for the fats information
//             Intent fatsIntent = new Intent(this, CarbsActivity.class);
//             startActivity(fatsIntent);
//             break;

            default:
                break;
        }

    }

}
