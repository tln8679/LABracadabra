package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProduceActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_two);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a2);
//        mp.start();
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                Intent intent = new Intent(ProduceActivityTwo.this, ProduceActivityThree.class);
//                startActivity(intent);
//            }
//        }, mp.getDuration());
    }

    public void onClick(View v){
        // Move to next question
        Intent intent = new Intent(this, ProduceActivityThree.class);
        startActivity(intent);
    }
}
