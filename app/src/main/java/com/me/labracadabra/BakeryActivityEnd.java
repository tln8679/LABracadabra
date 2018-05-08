package com.me.labracadabra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;

/**
 * @author tln86
 * Created by Taylor Noble on 05/01/2018.
 * Filename: BakeryBakeryEnd.java
 * Purpose: This program file controls the end of the bakery module. It saves the stars to the database
 *          and analyzes the scores
 * Revised: N/a
 * Data Structures: Nothing special
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, a better list of modules could be made
 */
public class BakeryActivityEnd extends AppCompatActivity {
    protected dbManager scoreHelper;

    /**
     * Created by Taylor Noble on 05/01/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     * Called: on create (screen started)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_end);
        logEvent();
        score();
        scoreHelper = new dbManager(this);
        scoreHelper.deleteScore(MagiciansActivity.getMagician(), GroceryActivity.getActivity());
        scoreHelper.insertNewScore(MagiciansActivity.getMagician(), BakeryActivityOne.getScore(),
                GroceryActivity.getActivity());
    }


    /**
     * @author tln8679
     * Created by Taylor Noble on 5/1/2018.
     * Purpose: Scores the user by incorrect choices and displays stars
     * Revised: 5/2/2018 - Changed variables to constant
     * Called: on create (screen started)
     * Revisions/extensions: Use better/different scoring algorithm
     */
    public void score() {
        int GOOD = 3;   // these following numbers represent number of incorrectMessage clicks
        int OKAY = 8;
        int BAD = 20;
        ImageView s1 = findViewById(R.id.s1);
        ImageView s2 = findViewById(R.id.s2);
        ImageView s3 = findViewById(R.id.s3);

        if (BakeryActivityOne.getScore() < GOOD) {
            s1.setBackgroundResource(R.drawable.ic_star);
            s2.setBackgroundResource(R.drawable.ic_star);
            s3.setBackgroundResource(R.drawable.ic_star);
        } else if (BakeryActivityOne.getScore() >= GOOD && BakeryActivityOne.getScore() < OKAY) {
            s2.setBackgroundResource(R.drawable.ic_star);
            s3.setBackgroundResource(R.drawable.ic_star);
        } else if (BakeryActivityOne.getScore() < BAD) {
            s3.setBackgroundResource(R.drawable.ic_star);
        }


    }

    /**
     * Created by Taylor Noble on 5/1/2018.
     *
     * @param v: the content view (resource layout xml file)
     *           Purpose: This method defines what happens when a button from the layout file is clicked.
     *           In this case, takes user back to main menu.
     *           Data Structures: n/a
     *           Possible revisions/extensions: none foreseen
     *           Called: When an object is clicked (finger tap). Takes user to main menu
     */
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mainmenu:
                // return to grocery menu
                Intent intent = new Intent(this, GroceryActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    /**
     * Created by Taylor Noble on 5/1/2018.
     * Purpose: Defines the the attributes and analytics we log to pinpoint
     * Output:  Attributes and metrics will be viewable from the AWS console
     */
    public void logEvent() {
        LandingScreen.pinpointManager.getSessionClient().startSession();
        final AnalyticsEvent event =
                LandingScreen.pinpointManager.getAnalyticsClient().createEvent("BakeryScores")
                        .withAttribute("Score", String.valueOf(BakeryActivityOne.getScore()))
                        .withMetric("DemoMetric1", (double) BakeryActivityOne.getScore());
        LandingScreen.pinpointManager.getAnalyticsClient().recordEvent(event);
        LandingScreen.pinpointManager.getSessionClient().stopSession();
        LandingScreen.pinpointManager.getAnalyticsClient().submitEvents();

    }

    /**
     * Created by Taylor Noble on 5/1/2018.
     * Purpose: Defines what happens when the phone back button is pressed
     * Output:  Resets the current screen
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GroceryActivity.class);
        startActivity(intent);
    }
}