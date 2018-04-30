package com.me.labracadabra;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author tln86
 * Created by Taylor Noble on 3/24/2018.
 * Filename: DeliActivityThree.java
 * Purpose: This program file controls the third deli activity. It is a learning module for kids to
 *          learn about science in the deli aisle of a grocery store.
 * Revised: 4/3/2018
 * Data Structures: Uses a hash map for the TextToSpeech API.
 * Reason for existence: Starts the deli learning module
 * Input: None
 * Extensions/Revisions: Given more specific content from the clients, and feed back from a focus
 *      group of children, better indirect learning games could be made
 */
public class DeliActivityThree extends AppCompatActivity {
    private ViewGroup mainLayout;
    private int xDelta;
    private int yDelta;
    private List<ImageView> correct = new ArrayList<>();
    private int cakeStartX, appleStartX, carrotStartX;
    private int cakeStartY, appleStartY, carrotStartY;
    private int top, bottom, left, right;
    private TextToSpeech reader;
    private HashMap<String, String> onlineSpeech = new HashMap<>();
    protected final int SPEECH_INIT_TIME = 400;
    public final String CORRECT = "correct";
    public final String INCORRECT = "incorrectMessage";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli_three);
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        // Incorrect choices
        // cake
        final ImageView cake = (ImageView) findViewById(R.id.chips);
        cake.setOnTouchListener(onTouchListener());
        //apple
        final ImageView apple = (ImageView) findViewById(R.id.apple);
        apple.setOnTouchListener(onTouchListener());
        // carrot
        final ImageView carrot = (ImageView) findViewById(R.id.carrot);
        carrot.setOnTouchListener(onTouchListener());
        // Correct choices
        final ImageView steak = (ImageView) findViewById(R.id.steak);
        steak.setOnTouchListener(onTouchListener());
        correct.add(steak);
        final ImageView fish = (ImageView) findViewById(R.id.fish);
        fish.setOnTouchListener(onTouchListener());
        correct.add(fish);
        final ImageView chix = (ImageView) findViewById(R.id.chix);
        chix.setOnTouchListener(onTouchListener());
        correct.add(chix);
        initReader();
    }

    /**
     * Created by Taylor Noble on 4/3/2018.
     * Purpose: Initialises the text to speech reader.
     * Important Note: Takes time after app launches to initialize, so I delay the speak function,
     *          so it will not occur before the reader is initialized.
     * Called: Called onCreate to initialise the reader
     * Possible revisions: Professional reader v.s. Text synthesizer
     */
    public void initReader(){
        final String initMessage = "Drag all of the meats onto the plate";
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
                sound(initMessage);
            }
        }, SPEECH_INIT_TIME);
    }

    /**
     * Created by Taylor Noble on 4/7/2018.
     * Purpose: Calls the text readers speak method
     * Possible revision: Making a static string and passing it through the method may make this
     *          function more reusable
     * Called: Called onCreate after text synthesizer is initialized
     */
    public void sound(String message) {
        reader.speak(message, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    /**
     * Created by Taylor Noble on 4/6/2018.
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

    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        // Incorrect choices
        // cake
        final ImageView cake = (ImageView) findViewById(R.id.chips);
        cakeStartX = (int) cake.getLeft();
        cakeStartY = (int) cake.getTop();
        //apple
        final ImageView apple = (ImageView) findViewById(R.id.apple);
        appleStartX = (int) apple.getLeft();
        appleStartY = (int) apple.getTop();
        // carrot
        final ImageView carrot = (ImageView) findViewById(R.id.carrot);
        carrotStartX = (int) carrot.getLeft();
        carrotStartY = (int) carrot.getTop();

        final ImageView container = (ImageView) findViewById(R.id.container);
        top = container.getTop();
        bottom = container.getBottom();
        left = container.getLeft();
        right = container.getRight();

    }

    public boolean isContained(ImageView image, int x, int y){
        if (x < right && x > left && y < bottom && y > top){
            for (Iterator<ImageView> iter = correct.listIterator(); iter.hasNext(); ) {
                ImageView a = iter.next();
                if (image == a) {
                    iter.remove();
                }
            }
            return true;
        }
        else return false;
    }


    /**
     *
      * @return
     */
    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                ImageView current = (ImageView) findViewById(view.getId());
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        String res;
                        if (correct.contains(current)){
                            ProgressBar bar = findViewById(R.id.Bar);
                            bar.incrementProgressBy(8);
                            res = "Correct";
                            sound(res);
                            Toast.makeText(DeliActivityThree.this,
                                    String.valueOf(correct.size()), Toast.LENGTH_SHORT)
                                    .show();
                            isContained(current, x, y);
                            if(correct.isEmpty()){
                                Intent intent = new Intent(DeliActivityThree.this, DeliActivityFour.class);
                                startActivity(intent);
                                break;
                            }
                        }

                        else{
                            DeliActivity.incrementScore();
                            res = "Incorrect";
                            sound(res);
                            if (current == findViewById(R.id.chips)) {
                                current.setX(cakeStartX);
                                current.setY(cakeStartY);
                            }
                            else if (current == findViewById(R.id.carrot)) {
                                current.setX(carrotStartX);
                                current.setY(carrotStartY);
                            }
                            else if (current == findViewById(R.id.apple)) {
                                current.setX(appleStartX);
                                current.setY(appleStartY);
                            }

                        }
                        Toast.makeText(DeliActivityThree.this,
                                res, Toast.LENGTH_SHORT)
                                .show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }
}


