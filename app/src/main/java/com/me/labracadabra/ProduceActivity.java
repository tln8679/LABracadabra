package com.me.labracadabra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class ProduceActivity extends AppCompatActivity {

    // Declare a DynamoDBMapper object
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce);
        // Instantiate audio
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.a1);
        mp.start();
        // Instantiate a AmazonDynamoDBMapperClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
    }

    public void onClick (View v){
        switch (v.getId()) {
            case R.id.banana:
                // Wrong answer
                MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
                mp.start();
                EditText editText = (EditText)findViewById(R.id.editText);
                TextView w1 = (TextView)findViewById(R.id.wrong);
                w1.setText("INCORRECT");
                String update = editText.getText().toString();
                int count = Integer.parseInt(update)+1;
                editText.setText(Integer.toString(count), TextView.BufferType.EDITABLE);
                break;

            case R.id.orange:
                // Wrong answer
                MediaPlayer mp2 = MediaPlayer.create(this, R.raw.wrong);
                mp2.start();
                EditText e1 = (EditText)findViewById(R.id.editText);
                TextView w2 = (TextView)findViewById(R.id.wrong);
                w2.setText("INCORRECT");
                String update1 = e1.getText().toString();
                int count1 = Integer.parseInt(update1)+1;
                e1.setText(Integer.toString(count1), TextView.BufferType.EDITABLE);
                break;

            case R.id.apples:
                // Wrong answer
                MediaPlayer mp3 = MediaPlayer.create(this, R.raw.wrong);
                mp3.start();
                EditText editText2 = (EditText)findViewById(R.id.editText);
                TextView w3 = (TextView)findViewById(R.id.wrong);
                w3.setText("INCORRECT");
                String update2 = editText2.getText().toString();
                int count2 = Integer.parseInt(update2)+1;
                editText2.setText(Integer.toString(count2), TextView.BufferType.EDITABLE);
                break;

            case R.id.chicken:
                // Move to next question
                MediaPlayer mp4 = MediaPlayer.create(this, R.raw.correct);
                mp4.start();
                // We don't want the audio to play over
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent intent = new Intent(ProduceActivity.this, ProduceActivityTwo.class);
                        startActivity(intent);
                    }
                },1450);
                break;
        }
    }
}
