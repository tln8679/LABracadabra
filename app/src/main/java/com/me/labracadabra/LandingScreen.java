package com.me.labracadabra;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;

public class LandingScreen extends AppCompatActivity {
    public static PinpointManager pinpointManager;
    /** Duration of wait **/
    private final int DISPLAY_LENGTH = 1250;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        AWSMobileClient.getInstance().initialize(this).execute();
        PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                getApplicationContext(),
                AWSMobileClient.getInstance().getCredentialsProvider(),
                AWSMobileClient.getInstance().getConfiguration());

        pinpointManager = new PinpointManager(pinpointConfig);
        // Start a session with Pinpoint
        pinpointManager.getSessionClient().startSession();

        // Stop the session and submit the default app started event
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();

        // Testing Custom Metrics
        logEvent();

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

    public void logEvent() {
        pinpointManager.getSessionClient().startSession();
        final AnalyticsEvent event =
                pinpointManager.getAnalyticsClient().createEvent("Custom Event")
                        .withAttribute("DemoAttribute1", "DemoAttributeValue1")
                        .withAttribute("DemoAttribute2", "DemoAttributeValue2")
                        .withMetric("DemoMetric1", Math.random());

        pinpointManager.getAnalyticsClient().recordEvent(event);
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();

    }
}
