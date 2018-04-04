package com.me.labracadabra;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.*;


public class GroceryActivity extends AppCompatActivity {
    private Intent intent;
    public static String activity;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    /**
     * adding a database
     */
    dbManager dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.grocery);
//        mp.start();
        reader = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
                    reader.setSpeechRate(.75f);

                }
            }
        });
        // wait a little for the initialization to complete
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sound();
            }
        }, 400);

        // Setting produce stars so they can be changed

        dbHelper = new dbManager(this);
        loadScores();
    }


    private void loadScores() {
        ArrayList<String> taskList = dbHelper.getTaskList(MagiciansActivity.magican);
        EditText test = (EditText) findViewById(R.id.testDB);
        String bestScore;
        if (taskList.size()>0) {
            bestScore = taskList.get(0);
            test.setText(bestScore + "\t" + taskList.size(), EditText.BufferType.EDITABLE);
        } else {
            bestScore = "0";
        }

        ImageView ps1 = (ImageView) findViewById(R.id.ps1);
        ImageView ps2 = (ImageView) findViewById(R.id.ps2);
        ImageView ps3 = (ImageView) findViewById(R.id.ps3);
        if (Integer.parseInt(bestScore) < 3 && Integer.parseInt(bestScore) != 0) {
            ps1.setBackgroundResource(R.drawable.ic_star);
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (Integer.parseInt(bestScore) >= 3 && Integer.parseInt(bestScore) < 8) {
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (Integer.parseInt(bestScore) < 20 && Integer.parseInt(bestScore) > 8) {
            ps3.setBackgroundResource(R.drawable.ic_star);
        }
    }


    /**
     * Opening script
     */
    public void sound() {
        String toSpeak = "Are you in the produce aisle, " +
                "the deli, dairy, dry goods, bakery, or canned goods section?";
        // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
    }


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

            /**case R.id.dairy:
             // Loads the screen for the protein information
             Intent proIntent = new Intent(this, ProteinActivity.class);
             startActivity(proIntent);
             break;

             case R.id.market:
             // Loads the screen for the protein information
             Intent carbsIntent = new Intent(this, CarbsActivity.class);
             startActivity(carbsIntent);
             break;

             case R.id.canned:
             // Loads the screen for the dairy information
             Intent dairyIntent = new Intent(this, DairyActivity.class);
             startActivity(dairyIntent);
             break;

             case R.id.bakery:
             // Loads the screen for the fats information
             Intent fatsIntent = new Intent(this, CarbsActivity.class);
             startActivity(fatsIntent);
             break;
             /**
             case R.id.buttonVitamins:
             // Loads the screen for the vitamins information
             Intent vitaminsIntent = new Intent(this, VitaminActivity.class);
             startActivity(vitaminsIntent);
             break;

             case R.id.buttonFiber:
             // Loads the screen for the fiber information
             Intent fiberIntent = new Intent(this, FiberActivity.class);
             startActivity(fiberIntent);
             break;
             */


            default:
                break;
        }

    }

}
