package com.me.labracadabra;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        dbManager quizDb = new dbManager(this);
        //  Change menu icon color
        //  Drawable icon = menu.getItem(0).getIcon();
        //  icon.mutate();
        //  icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonFruits:
                // Loads the screen for the fruit information
                Intent intent = new Intent(this, FruitActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonVegetables:
                // Loads the screen for the fruit information
                Intent vegIntent = new Intent(this, VegetablesActivity.class);
                startActivity(vegIntent);
                break;

            case R.id.buttonProtein:
                // do your code
                break;

            case R.id.buttonCarbs:
                // code
                break;

            case R.id.buttonDairy:
                // code
                break;

            case R.id.buttonFats:
                // code
                break;

            case R.id.buttonVitamins:
                // code
                break;

            case R.id.buttonFiber:
                // code
                break;

            case R.id.games:
                // start games screen
                break;

            case R.id.quizzes:
                // start quiz screen
                break;


            default:
                break;
        }

    }




}
