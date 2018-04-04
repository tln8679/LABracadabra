package com.me.labracadabra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class DeliActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli_two);
    }

    public void onClick (View v){
        ImageView muscle = (ImageView) findViewById(R.id.arm);
        muscle.animate().scaleXBy(2).scaleYBy(2);
    }
}
