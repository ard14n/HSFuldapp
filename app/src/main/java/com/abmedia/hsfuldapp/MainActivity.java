package com.abmedia.hsfuldapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView test = (TextView) findViewById(R.id.text);

        Grades testgrade = new Grades("test","test");

        String asiCode = testgrade.login();

        test.setText(asiCode);


    }
}