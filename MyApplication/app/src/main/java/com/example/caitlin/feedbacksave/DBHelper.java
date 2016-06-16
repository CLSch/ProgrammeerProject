package com.example.caitlin.feedbacksave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Caitlin on 16-06-16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "feedback.db";
    private static final int DATABASE_VERSION = 1;
    //private static final String TABLE = "Todos";

    // table names
    private static final String TABLE_YEARS = "Years";
    private static final String TABLE_SUBJECTS = "Subjects";
    private static final String TABLE_NOTES = "Notes";
    private static final String TABLE_PHOTOS = "Photos";

    // general key names
    public static final String KEY_ID = "_id"; // public of private?
    private static final String KEY_FB_TYPE = "Type";

    // years table column names
    private static final String KEY_YEARS = "Year";

    // subjects table column names
    private static final String KEY_SUBJECT = "Subject";

    // notes table column names
    private static final String KEY_NOTES = "Note";

    // photos table column names
    private static final String KEY_PHOTOS = "Photo";

    // create statements for tables
    private static final String CREATE_TABLE_YEARS = "CREATE TABLE " + TABLE_YEARS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_YEARS
            + " TEXT" + ")";

    private static final String CREATE_TABLE_SUBJECTS = "CREATE TABLE " + TABLE_SUBJECTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUBJECT + " TEXT"
            + ")";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NOTES + " TEXT," + KEY_FB_TYPE + " INTEGER" + ")";

    private static final String CREATE_TABLE_PHOTOS = "CREATE TABLE " + TABLE_PHOTOS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PHOTOS + " TEXT," + KEY_FB_TYPE + " INTEGER" + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Create database when helper object is made and there isn't one already */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_YEARS);
        db.execSQL(CREATE_TABLE_SUBJECTS);
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_PHOTOS);
    }

    /** upgrade database when helper object is made and there is one already */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }


    // ////////////////// YEAR
    // CREATE YEAR

    // GET ALL YEARS

    // niet maar eentje toch?

    ////////////////////// SUBJECTS
    // CREATE SUBJECT

    // GET SUBJECT

    // GET ALL SUBJECTS

    // UPDATE ALL SUBJECTS

    // DELETE SUBJECT

    ///////////////////// NOTES
    // CREATE NOTE

    // GET ALL NOTES

    // DELETE NOTE

    // GET NOTE

    //////////////////// PHOTOS
    // CREATE PHOTOS

    // GET ALL PHOTOS

    // DELETE NOTES

    // GET PHOTO

    /** create to-do's for in the database */
    public void createYear() {
        SQLiteDatabase db = getWritableDatabase();
        Long numRows;
        try {
            numRows = DatabaseUtils.queryNumEntries(db, TABLE_YEARS);
        } catch (Exception e) {
            Log.d("shit was", "empty");
            numRows = 0L;
        }

        String name = "Year " + String.valueOf(numRows);

        ContentValues values = new ContentValues();
        values.put(KEY_YEARS, name);
        db.insert(TABLE_YEARS, null, values);
        db.close();
    }

    public ArrayList<Year> readAllYears() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Year> years = new ArrayList<>();

        String query = "SELECT " + KEY_ID + ", " + KEY_YEARS + " FROM " + TABLE_YEARS;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                // MAAK YEAR OBJECT
                Year year = new Year();
                //year.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                //year.setNote((cursor.getString(cursor.getColumnIndex(KEY_YEARS))));

                // adding to todo list
                years.add(year);
            } while (cursor.moveToNext());
        }

        return years;
    }

    /** return a Arraylist Hashmap with all items in database */
    public ArrayList<HashMap<String, String>> read() {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<HashMap<String, String>> todoList = new ArrayList();

        //String query = "SELECT " + KEY_ID + ", " + KEY_TODO + " FROM " + TABLE;
        //Cursor cursor = db.rawQuery(query, null);

        // set cursor to the beginning of the database
//        if (cursor.moveToFirst()){
//            do {
//                // add id and to-do from current row to hashmap
//                HashMap<String, String> todoI = new HashMap<>();
//                todoI.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
//                todoI.put("todo", cursor.getString(cursor.getColumnIndex(KEY_TODO)));
//
//                todoList.add(todoI);
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return todoList;
//    }
//
//    /** delete to-do's from database */
//    public void delete(int id) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.delete(TABLE, " " + KEY_ID + " = ? ", new String[] {String.valueOf(id)});
//        db.close();
        return null;
    }
}
