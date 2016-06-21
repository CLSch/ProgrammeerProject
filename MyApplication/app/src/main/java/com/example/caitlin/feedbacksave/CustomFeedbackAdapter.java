package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
public class CustomFeedbackAdapter extends ArrayAdapter<String> {
    // wordt later waarschijnlijk arraylist met Feedback Objects
    ArrayList<String> feedback;
    CurrentSubjectActivity activity;
    String subject;
    //DBHelper helper;
    //StorageReference photoRef;
    //String photoRefPath;
    //DropboxAPI dropboxAPI;

    public CustomFeedbackAdapter (CurrentSubjectActivity activity, ArrayList<String> data, String subject) {
        super(activity, 0, data);
        this.feedback = data;
        this.activity = activity;
        this.subject = subject;
        //this.photoRefPath = ref;
        //this.dropboxAPI = dbApi;
    }

    /** get the view and return it*/
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        //final int thisPos = pos;

        DBHelper helper = new DBHelper(activity);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_items, parent, false);
        }

        final String thisListItem = feedback.get(pos);
        ArrayList<Photo> temp = helper.readAllPhotosPerSubject(subject);
        final int id = temp.get(pos).getId();

        final String path = helper.getPhotoPath(id);
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


                Intent photoFeedbackIntent = new Intent(activity, PhotoFeedback.class);
                photoFeedbackIntent.putExtra("filePath", path);
                activity.startActivity(photoFeedbackIntent);
            }
        });

        // delete todolists on longclick
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                activity.deletePhotoAlertDialog(path, id);
                return true;
            }
        });
        return view;
    }

}
