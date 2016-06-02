package com.example.caitlin.feedbacksave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class YearsActivity extends AppCompatActivity {
    // krijg list terug uit FireBase API?
    ArrayList<String> yearsList = new ArrayList<>();
    CustomYearsAdapter adapter;
    ListView lvYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

        yearsList.add("Year 1"); // HARDCODED !!!!
        yearsList.add("Year 2"); // HARDCODED !!!!

        makeAdapter();
    }

    public void makeAdapter(){
        adapter = new CustomYearsAdapter(this, yearsList);
        lvYears = (ListView) findViewById(R.id.lvYear);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvYears != null;
        lvYears.setAdapter(adapter);
    }

    public void addYearClick(View v) {
        Toast.makeText(this, "add a year", Toast.LENGTH_SHORT).show();
    }
}
