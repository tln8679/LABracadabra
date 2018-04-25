package com.me.labracadabra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UnderConstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_construction);
    }

    public void onClick(View v){
        Intent intent = new Intent(this, Locations.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, Locations.class);
        startActivity(intent);
    }
}
