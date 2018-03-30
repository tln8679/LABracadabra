package com.me.labracadabra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.database.sqlite.SQLiteDatabase;
import  android.content.ContentValues;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;

public class ProduceActivityFive extends AppCompatActivity {
    dbManager scoreHelper = new dbManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_five);
        logEvent();
        score();
    }

    public void score() {
        ImageView s1 = (ImageView) findViewById(R.id.s1);
        ImageView s2 = (ImageView) findViewById(R.id.s2);
        ImageView s3 = (ImageView) findViewById(R.id.s3);
        System.out.println(ProduceActivity.score);
        if (ProduceActivity.score < 3) {
            s1.setBackgroundResource(R.drawable.ic_star);
            s2.setBackgroundResource(R.drawable.ic_star);
            s3.setBackgroundResource(R.drawable.ic_star);
        } else if (ProduceActivity.score >= 3 && ProduceActivity.score < 8) {
            s2.setBackgroundResource(R.drawable.ic_star);
            s3.setBackgroundResource(R.drawable.ic_star);
        } else if (ProduceActivity.score < 20) {
            s3.setBackgroundResource(R.drawable.ic_star);
        }
    // Gets the data repository in write mode
        SQLiteDatabase db = scoreHelper.getWritableDatabase();

    // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(dbManager.COLUMN_NAME_MAGICIAN, MagiciansActivity.magican);
        values.put(dbManager.COLUMN_NAME_SCORE, ProduceActivity.score);
        values.put(dbManager.COLUMN_NAME_ACTIVITY, GroceryActivity.activity);


    // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(dbManager.TABLE_NAME, null, values);


    }

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

    public void logEvent() {
        LandingScreen.pinpointManager.getSessionClient().startSession();
        final AnalyticsEvent event =
                LandingScreen.pinpointManager.getAnalyticsClient().createEvent("Score Report")
                        .withAttribute("Incorrect Guesses", Integer.toString(ProduceActivity.score));
//                        .withAttribute("DemoAttribute2", "DemoAttributeValue2")
//                        .withMetric("DemoMetric1", Math.random());

        LandingScreen.pinpointManager.getAnalyticsClient().recordEvent(event);
        LandingScreen.pinpointManager.getSessionClient().stopSession();
        LandingScreen.pinpointManager.getAnalyticsClient().submitEvents();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scoreHelper.close();
        super.onDestroy();
    }

}
