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
    Context context;
    DBHelper helper;
    //DropboxAPI dropboxAPI;

    public CustomSubjectsAdapter (Context context, ArrayList<String> data) {
        super(context, 0, data);
        this.subjects = data;
        this.context = context;
        //this.dropboxAPI = dbApi;
    }

    /** get the view and return it*/
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        //final int thisPos = pos;

        helper = new DBHelper(context);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_items, parent, false);
        }

        final int thisPos = pos;
        final String thisListItem = subjects.get(pos);
        Log.d("dit is thislistitem", thisListItem);

//        // put Todolist names in textview for listview
        TextView tvList = (TextView) view.findViewById(R.id.tvInListView);
        tvList.setText(thisListItem);

        // start Currentlistactivity on click
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent currentSubjectsIntent = new Intent(context, CurrentSubjectActivity.class);
//                // geef alle feedback mee
//                currentSubjectsIntent.putExtra("subjectName", thisListItem);
//                context.startActivity(currentSubjectsIntent);
//            }
//        });

        // delete subjects on longclick
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                int id = subjects.get(thisPos).getId();
//
//                Toast.makeText(context, "item is deleted" , Toast.LENGTH_LONG).show();
//
//                //delete from database
//                helper.deleteSubject(id);
//
//                // delete from view, is dit nodig of kun je ook de adapter updaten?
//                subjects.remove(thisPos);
//
//                return true;
//            }
//        });

        return view;
    }


}
