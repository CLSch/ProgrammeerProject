package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Caitlin on 02-06-16.
 */
public class CustomFeedbackAdapter extends ArrayAdapter {
    // wordt later waarschijnlijk arraylist met Feedback Objects
    ArrayList<String> feedback;
    Context context;

    public CustomFeedbackAdapter (Context context, ArrayList<String> data) {
        super(context, 0, data);
        this.feedback = data;
        this.context = context;
    }

    /** get the view and return it*/
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        //final int thisPos = pos;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_items, parent, false);
        }

        final String thisListItem = feedback.get(pos);
//
//        // put Todolist names in textview for listview
        TextView tvList = (TextView) view.findViewById(R.id.tvInListView);
        tvList.setText(thisListItem);

        // start Currentlistactivity on click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OPEN DE FEEDBACK, HEB JE API VOOR NODIG?

                // CHECK OF FB EEN FOTO IS OF EEN MEMO EN OPEN DE JUISTE INTENT!!!

                Intent photoFeedbackIntent = new Intent(context, PhotoFeedback.class);
                // geef alle feedback mee
                //allSubjectsIntent.putExtra("NameTable", tableName);
                context.startActivity(photoFeedbackIntent);
            }
        });

        // delete todolists on longclick
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // MAAK EEN ALERT DIALOG

//                Toast.makeText(context, "item is deleted" , Toast.LENGTH_LONG).show();
//
//                // delete from map
//                map.remove(thisPos);
//
//                // delete from Singleton
//                TodoManagerSingleton.getInstance().deleteList(tableName);
                return true;
            }
        });
        return view;
    }

}
