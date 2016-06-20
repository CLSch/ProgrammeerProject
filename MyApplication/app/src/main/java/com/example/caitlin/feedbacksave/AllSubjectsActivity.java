package com.example.caitlin.feedbacksave;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class AllSubjectsActivity extends SuperActivity {
    ListView lvASubjects;
    ArrayList<String> subjectsList = new ArrayList<>();
    CustomSubjectsAdapter adapter;
    DBHelper helper;
    String alertInput;
    String year;
    int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);

        helper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        year = extras.getString("year");
//        assert year != null;
//        year = year.replaceAll("\\s+","");

        if (helper.itemExists(year) && !helper.readAllSubjectsPerYear(year).isEmpty()) {
            addSubjectsToList();
        }

//        subjectsList.add("Prog Project"); // HARDCODED !!!!
//        subjectsList.add("Heuristieken"); // HARDCODED !!!!

        makeAdapter(); // ook maken als er niks in staat?
    }

    public void makeAdapter(){
        adapter = new CustomSubjectsAdapter(this, subjectsList);
        lvASubjects = (ListView) findViewById(R.id.lvAllSubjects);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvASubjects != null;
        lvASubjects.setAdapter(adapter);
    }

    public void addSubjectsToList() {
        ArrayList<Subject> temp = helper.readAllSubjectsPerYear(year);
        for (int i = 0; i < temp.size(); i++) {
            subjectsList.add(temp.get(i).getName());
        }
    }

    // http://stackoverflow.com/questions/10903754/input-text-dialog-android
    public void makeNewSubjectAlertDialog(final String hint) {
        //TODO: vang SQLite injections? rare tekens af

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.make_subject));

        // Set up the input
        final EditText input = new EditText(this);
        input.setHint(hint);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().length() == 0) {
                    makeNewSubjectAlertDialog(getString(R.string.empty_name_error));

                    Log.d("ik zit in if", "onclick dialog");
                    return;
                }
                alertInput = input.getText().toString();
                Log.d("dit is alertinput", alertInput);

                for (int i = 0; i < subjectsList.size(); i++) {
                    if (alertInput.equals(subjectsList.get(i))) {
                        Log.d("in if click", "dialog");
                        makeNewSubjectAlertDialog(getString(R.string.unique_name_error));
                        return;
                        //input.setHint("Write here a unique subject name");
                    }
                }

                if(hint.equals("Create new name")) {
                    updateSubject();
                }
                else {
                    Log.d("createSubject", "ja");
                    createSubject();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void makeChangeSubjectAlertDialog(final String currentName, int pos) {
        //TODO: vang SQLite injections? rare tekens af
        ArrayList<Subject> subjects = helper.readAllSubjectsPerYear(year);
        _id = subjects.get(pos).getId();
        //// ^^^ dit in een aparte functie?


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.edit_subject));
        builder.setMessage(getString(R.string.choose_change_subject));

        // Set up the buttons
        // MOET CHANGE WORDEN
        builder.setPositiveButton(getString(R.string.change), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                makeNewSubjectAlertDialog(getString(R.string.create_new_name));
            }
        });
        // MOET CANCEL WORDEN
        builder.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                dialog.cancel();
            }
        });

        // MOET DELETE WORDEN
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                //delete from database
                helper.deleteSubject(_id);

                // delete from view, is dit nodig of kun je ook de adapter updaten?
                adapter.remove(currentName);
                Toast.makeText(AllSubjectsActivity.this, "item is deleted" , Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    public void updateSubject() {
        helper.updateSubject(alertInput, _id, year);
        adapter.clear();
        addSubjectsToList();
        Log.d("DIT IS SUBJECTSLIST", Integer.toString(subjectsList.size()));
        adapter.addAll();
        adapter.notifyDataSetChanged();
    }

    public void createSubject() {
        helper.createSubject(alertInput, year);
        adapter.clear();
        addSubjectsToList();
        Log.d("DIT IS SUBJECTSLIST", Integer.toString(subjectsList.size()));
        adapter.addAll();
        adapter.notifyDataSetChanged();
    }

    public void addSubjectClick(View v){
        // laat alert dialog zien voor de naam van het subject
        makeNewSubjectAlertDialog(getString(R.string.subject_name));
    }

    public void startCurrentSubActivity(String subject) {
        Intent currentSubjectsIntent = new Intent(this, CurrentSubjectActivity.class);
        // geef alle feedback mee
        currentSubjectsIntent.putExtra("subjectName", subject);
        this.startActivity(currentSubjectsIntent);
    }
}
