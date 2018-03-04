package com.me.labracadabra;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class LandingScreen extends AppCompatActivity {

    /** Duration of wait **/
    private final int DISPLAY_LENGTH = 1250;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_landing_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent MagicIntent = new Intent(LandingScreen.this,MagiciansActivity.class);
                LandingScreen.this.startActivity(MagicIntent);
                LandingScreen.this.finish();
            }
        }, DISPLAY_LENGTH);
    }
}
