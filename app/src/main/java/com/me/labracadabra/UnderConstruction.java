package com.me.labracadabra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * @author tln86
 * Created by Taylor Noble on 2/26/2018.
 * Filename: UnderConstruction.java
 * Purpose: This program file serves as a place holder
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: N/a
 * Reason for existence: Placeolder for locations not created
 * Input: None
 * Extensions/Revisions: Better graphics
 */
public class UnderConstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_construction);
    }

    /**
     * Created by Taylor Noble on 2/26/2018.
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a button from the layout file is clicked
     * Data Structures: An Intent
     * Possible revisions/extensions: none foreseen
     * Called: When an object is clicked (finger tap)
     */
    public void onClick(View v){
        Intent intent = new Intent(this, Locations.class);
        startActivity(intent);
    }

    /**
     * Created by Taylor Noble 4/26/2018
     * Purpose: User needs to complete the activity (designers choice)
     */
    public void onBackPressed()
    {
        Intent intent = new Intent(this, Locations.class);
        startActivity(intent);
    }
}