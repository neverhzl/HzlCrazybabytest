package com.hzl.crazybabytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    private ScrollView first;
    private MyScrollView second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first = (ScrollView) findViewById(R.id.first_scroll);
        second = (MyScrollView) findViewById(R.id.second_scroll);
        second.parentScrollView = first;
    }

}
