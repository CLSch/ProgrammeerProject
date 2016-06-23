/**
 * CustomYearsAdapter.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Caitlin on 02-06-16.
 */
public class CustomYearsAdapter extends ArrayAdapter<String>{
    // of StringArrayList?
    ArrayList<String> years;
    YearsActivity activity;
    //DropboxAPI dbApi;

    public CustomYearsAdapter (YearsActivity activity, ArrayList<String> data) {
        super(activity, 0, data);
        //this.dbApi = dBApi;
        this.years = data;
        this.activity = activity;
    }

    /** get the view and return it*/
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        //final int thisPos = pos;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_items, parent, false);
        }

        final int thisPos = pos;
        final String thisListItem = years.get(pos);
//
//        // put Todolist names in textview for listview
        TextView tvList = (TextView) view.findViewById(R.id.tvInListView);
        tvList.setText(thisListItem);

        // start Currentlistactivity on click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onclick is clicked", thisListItem);
                Intent allSubjectsIntent = new Intent(activity, AllSubjectsActivity.class);
                // geef alle vakken mee
                allSubjectsIntent.putExtra("year", thisListItem);
                activity.startActivity(allSubjectsIntent);
            }
        });

        // delete todolists on longclick
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                activity.deleteYearsAlertDialog(thisListItem, thisPos);
                return true;
            }
        });
        return view;
    }
}
