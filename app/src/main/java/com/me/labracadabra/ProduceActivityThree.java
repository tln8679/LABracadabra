package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class ProduceActivityThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_three);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a3);
//        mp.start();
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                Button next = (Button)findViewById(R.id.next);
//                next.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        // Perform action on click
//                        Intent intent = new Intent(ProduceActivityThree.this, ProduceActivityFour.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//        }, mp.getDuration());
    }

    public void onClick(View view) {
        // Maybe if we want to go back with the button option



    }
}
