package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class GroceryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.grocery);
        mp.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        dbManager quizDb = new dbManager(this);

        /*  Change menu icon color
          Drawable icon = menu.getItem(0).getIcon();
          icon.mutate();
          icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
*/
        return super.onCreateOptionsMenu(menu);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.produce:
                // Loads the screen for the fruit information
                Intent intent = new Intent(this, ProduceActivity.class);
                startActivity(intent);
                break;

           /** case R.id.deli:
                // Loads the screen for the fruit information
                Intent vegIntent = new Intent(this, VegetablesActivity.class);
                startActivity(vegIntent);
                break;

            case R.id.dairy:
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
