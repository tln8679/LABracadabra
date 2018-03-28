package com.me.labracadabra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProduceActivityFive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_produce_five);
    }

    public void onClick(View v){
        TextView result = (TextView) findViewById(R.id.finalscore);
        result.setText(Integer.toString(ProduceActivity.score), TextView.BufferType.EDITABLE);
    }

}
