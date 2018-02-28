package com.me.labracadabra;

import android.app.Activity;
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

        /*  Change menu icon color
          Drawable icon = menu.getItem(0).getIcon();
          icon.mutate();
          icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
*/
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
                // Loads the screen for the protein information
                Intent proIntent = new Intent(this, ProteinActivity.class);
                startActivity(proIntent);
                break;

            case R.id.buttonCarbs:
                // Loads the screen for the protein information
                Intent carbsIntent = new Intent(this, CarbsActivity.class);
                startActivity(carbsIntent);
                break;

            case R.id.buttonDairy:
                // Loads the screen for the dairy information
                Intent dairyIntent = new Intent(this, DairyActivity.class);
                startActivity(dairyIntent);
                break;

            case R.id.buttonFats:
                // Loads the screen for the fats information
                Intent fatsIntent = new Intent(this, FatsActivity.class);
                startActivity(fatsIntent);
                break;

            case R.id.buttonVitamins:
                // Loads the screen for the vitamins information
                Intent vitaminsIntent = new Intent(this, VitaminActivity.class);
                startActivity(vitaminsIntent);
                break;

            case R.id.buttonFiber:
                // Loads the screen for the fiber information
                Intent fiberIntent = new Intent(this, FiberActivity.class);
                startActivity(fiberIntent);
                break;

            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
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
