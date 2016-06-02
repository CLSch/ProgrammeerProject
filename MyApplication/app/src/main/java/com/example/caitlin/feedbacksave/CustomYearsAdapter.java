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
public class CustomYearsAdapter extends ArrayAdapter{
    // of StringArrayList?
    ArrayList<String> years;
    Context context;

    public CustomYearsAdapter (Context context, ArrayList<String> data) {
        super(context, 0, data);
        this.years = data;
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

//        final TodoList thisList = map.get(thisPos);
//        final String tableName = thisList.getTableName();
//
//        // put Todolist names in textview for listview
//        TextView listTv = (TextView) view.findViewById(R.id.tvTodo);
//        listTv.setText(tableName);

        // start Currentlistactivity on click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allSubjectsIntent = new Intent(context, AllSubjectsActivity.class);
                // geef alle vakken mee
                //allSubjectsIntent.putExtra("NameTable", tableName);
                context.startActivity(allSubjectsIntent);
            }
        });

        // delete todolists on longclick
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
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
