/**
 * CustomFeedbackAdapter.java
 * Caitlin Schäffers
 * 10580441
 */

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
 * Adapter for putting photo feedback in the listview in the CurrentSubjectActivity.
 */
public class CustomFeedbackAdapter extends ArrayAdapter<String> {
    ArrayList<String> feedback;
    CurrentSubjectActivity activity;
    String subject;

    public CustomFeedbackAdapter (CurrentSubjectActivity activity, ArrayList<String> data, String subject) {
        super(activity, 0, data);
        this.feedback = data;
        this.activity = activity;
        this.subject = subject;
    }

    /* Get the view and return it. */
    @Override
    public View getView(int pos, View view, ViewGroup parent) {

        DBHelper helper = new DBHelper(activity);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_items, parent, false);
        }

        // get the name from the photo feedback items
        final String thisListItem = feedback.get(pos);

        // get the id fron the photo feedback items
        ArrayList<Photo> temp = helper.readAllPhotosPerSubject(subject, UserIdSingleton.getInstance().getUserId());
        final int id = temp.get(pos).getId();

        // get the path from the photo feedback items
        final String path = helper.getPhotoPath(id, UserIdSingleton.getInstance().getUserId());

        // put feedback names in textview for listview
        TextView tvList = (TextView) view.findViewById(R.id.tvInListView);
        tvList.setText(thisListItem);

        // start PhotoFeedbackActivity on click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoFeedbackIntent = new Intent(activity, PhotoFeedbackActivity.class);
                photoFeedbackIntent.putExtra("filePath", path);
                activity.startActivity(photoFeedbackIntent);
            }
        });

        // prompt an alert dialog for deleting feedback on longclick
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
