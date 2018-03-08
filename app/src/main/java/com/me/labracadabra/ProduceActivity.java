package com.me.labracadabra;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce);
    }

    public void onClick (View v){
        switch (v.getId()) {
            case R.id.banana:
                // Wrong answer
                EditText editText = (EditText)findViewById(R.id.editText);
                TextView w1 = (TextView)findViewById(R.id.wrong);
                w1.setText("INCORRECT");
                String update = editText.getText().toString();
                int count = Integer.parseInt(update)+1;
                editText.setText(Integer.toString(count), TextView.BufferType.EDITABLE);
                break;

            case R.id.orange:
                // Wrong answer
                EditText e1 = (EditText)findViewById(R.id.editText);
                TextView w2 = (TextView)findViewById(R.id.wrong);
                w2.setText("INCORRECT");
                String update1 = e1.getText().toString();
                int count1 = Integer.parseInt(update1)+1;
                e1.setText(Integer.toString(count1), TextView.BufferType.EDITABLE);
                break;

            case R.id.apples:
                // Wrong answer
                EditText editText2 = (EditText)findViewById(R.id.editText);
                TextView w3 = (TextView)findViewById(R.id.wrong);
                w3.setText("INCORRECT");
                String update2 = editText2.getText().toString();
                int count2 = Integer.parseInt(update2)+1;
                editText2.setText(Integer.toString(count2), TextView.BufferType.EDITABLE);
                break;
            case R.id.chicken:
                // Move to next question
                Intent intent = new Intent(this, ProduceActivityTwo.class);
                startActivity(intent);
                break;
        }
    }
}
