/**
 * AllSubjectsActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * This activity shows all subjects a user added. Adding, deleting and changing subjects gets
 * handled here.
 */
public class AllSubjectsActivity extends SuperActivity {
    ListView lvASubjects;
    ArrayList<String> subjectsList = new ArrayList<>();
    CustomSubjectsAdapter adapter;
    DBHelper helper;
    String alertInput;
    String year;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);

        // instantiate DBhelper class
        helper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        year = extras.getString("year");

        // check first if subject-year items exist, so searching subjects in database doesn't fail
        if (helper.subjectYearItemExists(year, UserIdSingleton.getInstance().getUserId()) &&
                !helper.readAllSubjectsPerYear(year, UserIdSingleton.getInstance().getUserId()).isEmpty()) {
            // add subjects to list if there are subjects in the database for this user and year
            addSubjectsToList();
        }

        makeAdapter();
    }

    /* Set up the CustomSubjectsAdapter and fill with subjects in list. */
    public void makeAdapter(){
        adapter = new CustomSubjectsAdapter(this, subjectsList);
        lvASubjects = (ListView) findViewById(R.id.lvAllSubjects);
        assert lvASubjects != null;
        lvASubjects.setAdapter(adapter);
    }

    /* Fill subjectsList with subjects from the database. */
    public void addSubjectsToList() {
        subjectsList.clear();
        ArrayList<Subject> temp = helper.readAllSubjectsPerYear(year, UserIdSingleton.getInstance().getUserId());
        for (int i = 0; i < temp.size(); i++) {
            subjectsList.add(temp.get(i).getName());
        }
    }

    /* Alert dialog for creating subjects and changing the name of existing subjects. */
    public void makeNewSubjectAlertDialog(final String hint) {
        // set up alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.make_subject));

        // set up the inputfield
        final EditText input = new EditText(this);
        input.setHint(hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // set up the buttons for the dialog
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // don't continue the dialog if the inputfield is empty
                if (input.getText().length() == 0) {
                    makeNewSubjectAlertDialog(getString(R.string.empty_name_error));
                    return;
                }
                alertInput = input.getText().toString();

                if (hint.equals(getString(R.string.create_new_name))) {
                    updateSubject();
                }
                else {
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

    /* Alert dialog to delete subjects or change name of subjects on longclick. */
    public void makeChangeSubjectAlertDialog(final String currentName, int pos) {
        ArrayList<Subject> subjects = helper.readAllSubjectsPerYear(year, UserIdSingleton.getInstance().getUserId());
        id = subjects.get(pos).getId();

        // set up alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.edit_subject));
        builder.setMessage(getString(R.string.choose_change_subject));

        // set up the buttons
        builder.setPositiveButton(getString(R.string.change), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                makeNewSubjectAlertDialog(getString(R.string.create_new_name));
            }
        });
        builder.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                //delete from database
                helper.deleteSubject(id, UserIdSingleton.getInstance().getUserId());

                // delete from view, is dit nodig of kun je ook de adapter updaten?
                adapter.remove(currentName);
                Toast.makeText(AllSubjectsActivity.this, R.string.item_is_deleted , Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    /* Update subject name in database and view. */
    public void updateSubject() {
        helper.updateSubject(alertInput, id, year, UserIdSingleton.getInstance().getUserId());
        adapter.clear();
        addSubjectsToList();
        adapter.addAll();
        adapter.notifyDataSetChanged();
    }

    /* Create subject in database and change view. */
    public void createSubject() {
        helper.createSubject(alertInput, year, UserIdSingleton.getInstance().getUserId());
        adapter.clear();
        addSubjectsToList();
        adapter.addAll();
        adapter.notifyDataSetChanged();
    }

    /* Make an alert dialog when add button gets clicked. */
    public void addSubjectClick(View v){
        makeNewSubjectAlertDialog(getString(R.string.subject_name));
    }

    /* Go to CurrentSubjectActivity when clicking on a subject. */
    public void startCurrentSubActivity(String subject) {
        Intent currentSubjectsIntent = new Intent(this, CurrentSubjectActivity.class);
        currentSubjectsIntent.putExtra("subjectName", subject);
        this.startActivity(currentSubjectsIntent);
    }
}
