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
import java.util.HashMap;


/**
 * @author tln86
 * Created by Taylor Noble on 2/05/2018.
 * Filename: LandingScreen.java
 * Purpose: This program file creates a landing screen for the application. It presents the
 *          application title and logo on the screen, says welcome, and then transitions
 *          to the next screen after a set quantam.
 * Revised: 2/12/2018
 * Data Structures: Uses a hash map for the TextToSpeech API.
 * Reason for existence: Landing page gives it a professional look
 * Input: None
 * Extensions/Revisions: Given better graphics we could make the layout look better by changing the
 *          resource .xml file associated with the landing screen
 */
public class LandingScreen extends AppCompatActivity {
    public static PinpointManager pinpointManager;  // Test for aws logging (passed)
    protected final int DISPLAY_LENGTH = 2700;    //  Length of time before transition to next screen
    private TextToSpeech reader;    //  Test synthesizer.
    //  Used to find a suitable voice from network synthesizer and pass it to the voice
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;


    @Override
    /**
     * Created by Taylor Noble on 2/05/2018.
     * If there is data in the Bundle, the activity will restore to it's previous state
     * Bundle is the default param for onCreate
     * Revised: 4/7/2018 - Broke this down into multiple functions for readability
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
        awsInit();  //  Connects to AWS backend
        logEvent(); // Testing Custom Metrics
        initReader(); // Text reader greets the user

        //  After the display length time is over, new intent is started (transition)
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create the Intent that will start the Menu-Activity
                Intent MagicIntent = new Intent(LandingScreen.this,MagiciansActivity.class);
                LandingScreen.this.startActivity(MagicIntent);
                LandingScreen.this.finish();
            }
        }, DISPLAY_LENGTH);
    }

    /**
     * Created by Taylor Noble on 4/07/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     *  Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader(){
        //  Creating a text2speech reader
        reader=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
                    reader.setSpeechRate(.75f);
                }
            }
        });
        // wait a little for the initialization to complete
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                sound();
            }
        }, SPEECH_INIT_TIME);
    }

    /**
     * Created by Taylor Noble on 3/6/2018.
     * Purpose: Used to track data using the AWS pinpoint service
     * Possible revisions:  Google Analytics may be a better option for custom metrics
     */
    public void awsInit(){
        AWSMobileClient.getInstance().initialize(this).execute();   // Connecting to aws backend
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
    }

    /**
     * Created by Taylor Noble on 4/7/2018.
     * Purpose: Calls the text readers speak method
     * Possible revision: Making a static string and passing it through the method may make this
     *          function more reusable
     */
    public void sound(){
        String toSpeak = "Welcome to labracadabra!";
        reader.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    /**
     * Created by Taylor Noble on 3/6/2018.
     * Purpose: Defines the the attributes and analytics we log to pinpoint
     * Output:  Attributes and metrics will be viewable from the AWS console
     */
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

    /**
     * Created by Taylor Noble on 3/6/2018.
     * Purpose: If the screen is paused (app minimized, user launches next screen via some input
     *          action, etc.), the reader needs to be killed.
     * Output:  None
     */
    public void onPause() {
        super.onPause();
        if (reader != null) {
            reader.stop();
            reader.shutdown();
        }
    }
}
