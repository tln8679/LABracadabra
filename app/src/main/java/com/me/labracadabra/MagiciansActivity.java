package com.me.labracadabra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MagiciansActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magicians);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mOne:
                // Loads the screen for the Locations
                // Needs to load the saved state for the first magician
                Intent locationsIntent = new Intent(this, Locations.class);
                startActivity(locationsIntent);
                break;

            case R.id.mTwo:
                // Loads the screen for the Locations
                // Needs to load the saved state for the second magician;
                Intent lIntent = new Intent(this, Locations.class);
                startActivity(lIntent);
                break;

            case R.id.mThree:
                // Loads the screen for the Locations
                // Needs to load the saved state for the third magician
                Intent lThreeIntent = new Intent(this, Locations.class);
                startActivity(lThreeIntent);
                break;
        }
    }
}
