package com.me.labracadabra;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.HashMap;
import android.database.Cursor;
import android.widget.TextView;

import java.util.*;


public class GroceryActivity extends AppCompatActivity {
    private Intent intent;
    public static String activity;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    dbManager scoreHelper = new dbManager(this);
    SQLiteDatabase db = scoreHelper.getReadableDatabase();
    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
    String[] projection = {
            dbManager._ID,
            dbManager.COLUMN_NAME_MAGICIAN,
            dbManager.COLUMN_NAME_SCORE,
            dbManager.COLUMN_NAME_ACTIVITY
    };

    // Filter results WHERE "title" = 'My Title'
    String selection = dbManager.COLUMN_NAME_MAGICIAN + " = ?";
    String[] selectionArgs = { "magician1" };

    // How you want the results sorted in the resulting Cursor
    String sortOrder =
            dbManager.COLUMN_NAME_SCORE + " DESC";

    Cursor cursor = db.query(
            dbManager.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
    );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.grocery);
//        mp.start();
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
                sound();
            }
        }, 400);

        // Setting produce stars so they can be changed
        score();
    }

    public void score() {
        ImageView ps1 = (ImageView) findViewById(R.id.ps1);
        ImageView ps2 = (ImageView) findViewById(R.id.ps2);
        ImageView ps3 = (ImageView) findViewById(R.id.ps3);
        if (ProduceActivity.score !=0 && ProduceActivity.score < 3) {
            ps1.setBackgroundResource(R.drawable.ic_star);
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (ProduceActivity.score >= 3 && ProduceActivity.score < 8) {
            ps2.setBackgroundResource(R.drawable.ic_star);
            ps3.setBackgroundResource(R.drawable.ic_star);
        } else if (ProduceActivity.score < 20 && ProduceActivity.score >=8) {
            ps3.setBackgroundResource(R.drawable.ic_star);
        }
        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(dbManager._ID));
            itemIds.add(itemId);
        }
        cursor.close();
        EditText test = (EditText) findViewById(R.id.testDB);
        test.setText(Integer.toString(itemIds.size()), EditText.BufferType.EDITABLE);
    }

    /**
     * Opening script
     */
    public void sound(){
        String toSpeak = "Are you in the produce aisle, " +
                "the deli, dairy, dry goods, bakery, or canned goods section?";
        // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(reader !=null){
            reader.stop();
            reader.shutdown();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(reader !=null){
            reader.stop();
            reader.shutdown();
        }
        scoreHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        dbManager quizDb = new dbManager(this);
        return super.onCreateOptionsMenu(menu);
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
