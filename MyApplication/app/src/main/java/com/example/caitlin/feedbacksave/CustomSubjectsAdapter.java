/**
 * CustomSubjectsAdapter.java
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
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;

import java.util.ArrayList;

/**
 * Created by Caitlin on 02-06-16.
 */
public class CustomSubjectsAdapter extends ArrayAdapter<String> {
    ArrayList<String> subjects;
    AllSubjectsActivity activity;
    //DropboxAPI dropboxAPI;

    public CustomSubjectsAdapter (AllSubjectsActivity act, ArrayList<String> data) {
        super(act.getApplicationContext(), 0, data);
        this.subjects = data;
        this.activity = act;
        //this.dropboxAPI = dbApi;
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
        final String thisListItem = subjects.get(pos);
        Log.d("dit is thislistitem", thisListItem);

//        // put Todolist names in textview for listview
        TextView tvList = (TextView) view.findViewById(R.id.tvInListView);
        tvList.setText(thisListItem);

        // start Currentlistactivity on click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startCurrentSubActivity(thisListItem);
            }
        });

        // delete subjects on longclick
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("setonitemlongclick", String.valueOf(thisPos));

                activity.makeChangeSubjectAlertDialog(thisListItem, thisPos);
                return true;
            }
        });

        return view;
    }


}
