package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Locations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.loc);
        mp.start();
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.groceries:
                // Loads the screen for the Grocery store
                Intent groceryIntent = new Intent(this, GroceryActivity.class);
                startActivity(groceryIntent);
                break;

            case R.id.hardware:
                // Loads the screen for the hardware store
                break;

            case R.id.library:
                // Loads the screen for the library
                break;

            case R.id.park:
                // Loads the screen for the park
                break;
        }
    }
}
