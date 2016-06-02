package com.example.caitlin.feedbacksave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class YearsActivity extends AppCompatActivity {
    // krijg list terug uit FireBase API?
    //ArrayList yearsList = new ArrayList();
    //CustomYearsAdapter adapter;
    ListView lvYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);


        makeAdapter();
    }

    public void makeAdapter(){
        lvYears = (ListView) findViewById(R.id.lvYear);
    }
}
