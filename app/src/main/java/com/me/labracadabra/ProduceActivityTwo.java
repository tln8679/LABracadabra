package com.me.labracadabra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProduceActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_two);
    }

    public void onClick(View v){
        // Move to next question
        Intent intent = new Intent(this, ProduceActivityThree.class);
        startActivity(intent);
    }
}
