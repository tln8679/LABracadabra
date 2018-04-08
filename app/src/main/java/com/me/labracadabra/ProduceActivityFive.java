package com.me.labracadabra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;

/**
 * @author tln86
 * Created by Taylor Noble on 2/26/2018.
 * Filename: ProduceActivityFive.java
 * Purpose: This program file completes the produce section. It returns the amount of stars
 *          earned, logs to AWS, and puts the info into the SQLite dB
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Uses a hash map for the TextToSpeech API. Strings and ints.
 * Reason for existence: Contains all of the learning modules for the grocery store.
 * Input: None
 * Extensions/Revisions: Different metrics for logging to aws could be used. Might have to
 *      create another dB column
 */
public class ProduceActivityFive extends AppCompatActivity {
    protected dbManager scoreHelper;


    /**
     * Created by Taylor Noble on 2/26/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     * Called: on create (screen started)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_five);
        logEvent();
        score();
        scoreHelper = new dbManager(this);
        scoreHelper.deleteScore (MagiciansActivity.getMagician(), GroceryActivity.getActivity());
        scoreHelper.insertNewScore(MagiciansActivity.getMagician(), ProduceActivity.getScore(),
                GroceryActivity.getActivity());
    }


    /**
     * @author tln8679
     * Created by Taylor Noble on 2/26/2018.
     * Purpose: Scores the user by incorrect choices and displays stars
     * Revised: 4/7/2018 - Changed variables to constant
     * Called: on create (screen started)
     * Revisions/extensions: Use better/different scoring algorithm
     */
    public void score() {
        int GOOD = 3;   // these following numbers represent number of incorrect clicks
        int OKAY = 8;
        int BAD = 20;
        ImageView s1 = (ImageView) findViewById(R.id.s1);
        ImageView s2 = (ImageView) findViewById(R.id.s2);
        ImageView s3 = (ImageView) findViewById(R.id.s3);
        System.out.println(ProduceActivity.getScore());
        if (ProduceActivity.getScore() < GOOD) {
            s1.setBackgroundResource(R.drawable.ic_star);
            s2.setBackgroundResource(R.drawable.ic_star);
            s3.setBackgroundResource(R.drawable.ic_star);
        } else if (ProduceActivity.getScore() >= GOOD && ProduceActivity.getScore() < OKAY) {
            s2.setBackgroundResource(R.drawable.ic_star);
            s3.setBackgroundResource(R.drawable.ic_star);
        } else if (ProduceActivity.getScore() < BAD) {
            s3.setBackgroundResource(R.drawable.ic_star);
        }


    }

    /**
     * Created by Taylor Noble on 2/26/2018.
     *
     * @param v: the content view (resource layout xml file)
     * Purpose: This method defines what happens when a button from the layout file is clicked.
     *         In this case, takes user back to main menu.
     * Data Structures: n/a
     * Possible revisions/extensions: none foreseen
     * Called: When an object is clicked (finger tap). Takes user to main menu
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
     * Created by Taylor Noble on 3/6/2018.
     * Purpose: Defines the the attributes and analytics we log to pinpoint
     * Output:  Attributes and metrics will be viewable from the AWS console
     */
    public void logEvent() {
        LandingScreen.pinpointManager.getSessionClient().startSession();
        final AnalyticsEvent event =
                LandingScreen.pinpointManager.getAnalyticsClient().createEvent("Custom Event")
                .withAttribute("DemoAttribute1", "DemoAttributeValue1")
                .withAttribute("DemoAttribute2", "DemoAttributeValue2")
                .withMetric("DemoMetric1", (double)ProduceActivity.getScore());
        LandingScreen.pinpointManager.getAnalyticsClient().recordEvent(event);
        LandingScreen.pinpointManager.getSessionClient().stopSession();
        LandingScreen.pinpointManager.getAnalyticsClient().submitEvents();

    }



}
