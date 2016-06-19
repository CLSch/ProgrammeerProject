package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;

import java.util.ArrayList;

/**
 * Created by Caitlin on 02-06-16.
 */
public class CustomYearsAdapter extends ArrayAdapter<String>{
    // of StringArrayList?
    ArrayList<String> years;
    Context context;
    //DropboxAPI dbApi;

    public CustomYearsAdapter (Context context, ArrayList<String> data) {
        super(context, 0, data);
        //this.dbApi = dBApi;
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
                Intent allSubjectsIntent = new Intent(context, AllSubjectsActivity.class);
                // geef alle vakken mee
                allSubjectsIntent.putExtra("year", thisListItem);
                context.startActivity(allSubjectsIntent);
            }
        });

        // delete todolists on longclick
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // EVENTUEEL EEN JAAR VERWIJDEREN??? DOE MAAR WEL???
                // DOE WEL EEN ALERT DIALOG MET "R U SURE?"

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
