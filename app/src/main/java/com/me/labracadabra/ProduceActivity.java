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

            case R.id.chips:
                // Wrong answer
                EditText editText = (EditText)findViewById(R.id.editText);
                String update = editText.getText().toString();
                int count = Integer.parseInt(update)+1;
                editText.setText(Integer.toString(count), TextView.BufferType.EDITABLE);
                break;

            case R.id.cake:
                // Wrong answer
                EditText e1 = (EditText)findViewById(R.id.editText);
                String update1 = e1.getText().toString();
                int count1 = Integer.parseInt(update1)+1;
                e1.setText(Integer.toString(count1), TextView.BufferType.EDITABLE);
                break;

            case R.id.produce:
                // Wrong answer
                EditText editText2 = (EditText)findViewById(R.id.editText);
                String update2 = editText2.getText().toString();
                int count2 = Integer.parseInt(update2)+1;
                editText2.setText(Integer.toString(count2), TextView.BufferType.EDITABLE);
                break;
            case R.id.doctor:
                // Move to next question
                break;
        }
    }
}
