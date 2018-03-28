package com.me.labracadabra;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ProduceActivityFour extends AppCompatActivity {
    boolean a, d, e, k = false;
    Button lc = null; // Stores last clicked button
    int count = 0;
    ArrayList<Button> choices = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_four);
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a4);
//        mp.start();
        choices.add((Button) findViewById(R.id.vitamin_a));
        choices.add((Button) findViewById(R.id.vitamin_d));
        choices.add((Button) findViewById(R.id.vitamin_e));
        choices.add((Button) findViewById(R.id.vitamin_k));
    }



    public void onClick (View v){
        switch (v.getId()) {
            case R.id.vitamin_a:
                if (!a) {
                    lc = (Button) findViewById(R.id.vitamin_a);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }
                break;

            case R.id.vitamin_d:
                if(!d) {
                    lc = (Button) findViewById(R.id.vitamin_d);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }

                break;

            case R.id.vitamin_e:
                if(!e) {
                    lc = (Button) findViewById(R.id.vitamin_e);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }

                break;

            case R.id.vitamin_k:
                if(!k) {
                    lc = (Button) findViewById(R.id.vitamin_k);
                    for (Button b : choices) {
                        if (b != lc) {
                            b.setBackgroundResource(R.drawable.border_button);
                        } else {
                            lc.setBackgroundResource(R.drawable.selected_button);
                        }
                    }
                }
                break;

            case R.id.eye:
                Button vita = (Button) findViewById(R.id.vitamin_a);
                Button eye = (Button) findViewById(R.id.eye);
                if (lc==vita && !a) {
                    vita.setBackgroundColor(Color.GREEN);
                    count += 1;
                    a = true;
//                    MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
//                    mp.start();
                    // Remove button from list, so it's color won't change again
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vita){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null) {
//                    MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
//                    mp.start();
                    lc.setBackgroundColor(Color.RED);
                    eye.animate().rotationBy(900);
                    ProduceActivity.score +=1;
                }
                // When all 4 are correct, start next screen
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;

            case R.id.bone:
                Button vitd = (Button) findViewById(R.id.vitamin_d);
                Button bone = (Button) findViewById(R.id.bone);
                if (lc==vitd && !d) {
                    vitd.setBackgroundColor(Color.GREEN);
                    count += 1;
                    d = true;
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
//                    mp.start();
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vitd){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null){
//                    MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
//                    mp.start();
                    lc.setBackgroundColor(Color.RED);
                    bone.animate().rotationBy(900);
                    ProduceActivity.score +=1;
                }
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;

            case R.id.lungs:
                Button vite = (Button) findViewById(R.id.vitamin_e);
                Button lungs = (Button) findViewById(R.id.lungs);
                if (lc==vite && !e) {
                    vite.setBackgroundColor(Color.GREEN);
                    count += 1;
//                    MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
//                    mp.start();
                    e = true;
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vite){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null){
//                    MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
//                    mp.start();
                    lc.setBackgroundColor(Color.RED);
                    lungs.animate().rotationBy(900);
                    ProduceActivity.score +=1;
                }
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;

            case R.id.blood:
                Button vitk = (Button) findViewById(R.id.vitamin_k);
                Button blood = (Button) findViewById(R.id.blood);
                if (lc==vitk && !k) {
                    vitk.setBackgroundColor(Color.GREEN);
                    count += 1;
//                    MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
//                    mp.start();
                    k = true;
                    for(int i =0; i <choices.size(); i++){
                        if(choices.get(i) == vitk){
                            choices.remove(i);
                        }
                    }
                }
                else if(lc!=null){
//                    MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
//                    mp.start();
                    lc.setBackgroundColor(Color.RED);
                    blood.animate().rotationBy(900);
                    ProduceActivity.score +=1;
                }
                if (count == 4){
                    Intent intent = new Intent(this, ProduceActivityFive.class);
                    startActivity(intent);}
                lc = null;
                break;
        }

    }
}
