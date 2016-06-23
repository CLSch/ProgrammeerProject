/**
 * CustomSubjectsAdapter.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Adapter for putting subjects in the listview in the AllSubjectsActivity.
 */
public class CustomSubjectsAdapter extends ArrayAdapter<String> {
    ArrayList<String> subjects;
    AllSubjectsActivity activity;

    public CustomSubjectsAdapter (AllSubjectsActivity act, ArrayList<String> data) {
        super(act.getApplicationContext(), 0, data);
        this.subjects = data;
        this.activity = act;
    }

    /** Get the view and return it. */
    @Override
    public View getView(int pos, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_items, parent, false);
        }

        final int thisPos = pos;
        final String thisListItem = subjects.get(pos);

        // put subject names in textview for listview
        TextView tvList = (TextView) view.findViewById(R.id.tvInListView);
        tvList.setText(thisListItem);

        // start CurrentSubjectActivity on click
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
                activity.makeChangeSubjectAlertDialog(thisListItem, thisPos);
                return true;
            }
        });

        return view;
    }


}
