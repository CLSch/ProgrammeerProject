/**
 * DBHelper.java
 * Caitlin Schäffers
 * 10580441
 */
package com.example.caitlin.feedbacksave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * In deze class staan alle functies voor de SQLite Database.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "feedbackTest17.db";
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

    // subjects table subject by year
    private static final String KEY_SUBJECT_YEAR = "SubjectYear";

    // notes table column names
    private static final String KEY_NOTES = "Note";

    // photos table column names
    private static final String KEY_PHOTOS = "Photo";

    private static final String KEY_PHOTOS_PATH = "PhotoPath";

    // photo table id for which subject contains this feedback
    private static final String KEY_PHOTO_SUBJECT = "PhotoSubject";

    // create statements for tables
    private static final String CREATE_TABLE_YEARS = "CREATE TABLE " + TABLE_YEARS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_YEARS + " TEXT)";

    private static final String CREATE_TABLE_SUBJECTS = "CREATE TABLE " + TABLE_SUBJECTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUBJECT + " TEXT,"
            + KEY_SUBJECT_YEAR + " TEXT)";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NOTES + " TEXT," + KEY_FB_TYPE + " INTEGER)";

    private static final String CREATE_TABLE_PHOTOS = "CREATE TABLE " + TABLE_PHOTOS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHOTOS + " TEXT,"
            + KEY_PHOTO_SUBJECT + " TEXT," + KEY_PHOTOS_PATH + " TEXT," + KEY_FB_TYPE + " INTEGER)";

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
        Log.d("onCreate", "gets called");
    }

    /** upgrade database when helper object is made and there is one already */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
        Log.d("onUpgrade", "gets called");
    }


    // ////////////////// YEAR
    // CREATE YEAR
    /** create years for in the database */
    public void createYear() {
        SQLiteDatabase db = getWritableDatabase();
        Long numRows;
        try {
            numRows = DatabaseUtils.queryNumEntries(db, TABLE_YEARS);
        } catch (Exception e) {
            Log.d("shit was", "empty");
            numRows = 0L;
        }

        String name = "Year " + String.valueOf(numRows + 1);

        ContentValues values = new ContentValues();
        values.put(KEY_YEARS, name);
        db.insert(TABLE_YEARS, null, values);
        db.close();
    }

    // GET ALL YEARS
    public ArrayList<Year> readAllYears() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Year> years = new ArrayList<>();
        //String query = "SELECT " + KEY_ID + ", " + KEY_YEARS + " FROM " + TABLE_YEARS;
        String query = "SELECT * FROM " + TABLE_YEARS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Year year = new Year();
                year.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                year.setName((cursor.getString(cursor.getColumnIndex(KEY_YEARS))));

                // adding to years arraylist
                years.add(year);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return years;
    }

    // niet maar eentje toch?

    ////////////////////// SUBJECTS
    // CREATE SUBJECT
    public void createSubject(String name, String year) {
        Log.d("in create year", year);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SUBJECT, name);
        values.put(KEY_SUBJECT_YEAR, year);
        db.insert(TABLE_SUBJECTS, null, values);
        db.close();
    }

    // GET SUBJECT
    // is dit echt nodig???

    // GET ALL SUBJECTS
    public ArrayList<Subject> readAllSubjectsPerYear(String year) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> subjects = new ArrayList<>();

//        String query = "SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_SUBJECT_YEAR + " = ?", year;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_SUBJECT_YEAR + " = ?", new String[] {year});
        if (cursor.moveToFirst()) {
            do {
                // MAAK subject OBJECT
                Subject subject = new Subject();
                subject.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                subject.setName((cursor.getString(cursor.getColumnIndex(KEY_SUBJECT))));

                // adding to years arraylist
                subjects.add(subject);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return subjects;
    }

    // stackoverflow
    public boolean yearItemExists(String year) {
        SQLiteDatabase db = getReadableDatabase();

        // Query 1 row
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_SUBJECT_YEAR + " = ?", new String[] {year});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // UPDATE ALL SUBJECTS
    // je kan dit ook doen door het item te verwijderen en opnieuw aan te maken
    public void updateSubject(String name, int id, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SUBJECT, name);

        db.update(TABLE_SUBJECTS, values, KEY_ID + " = ? AND " + KEY_SUBJECT_YEAR + " = ?",
                new String[] {String.valueOf(id), year});
    }

    // DELETE SUBJECT
    // kan ook met de naam gedaan worden als dat handiger is
    public void deleteSubject(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SUBJECTS, " " + KEY_ID + " = ? ", new String[] {String.valueOf(id)});
        db.close();
    }

    ///////////////////// NOTES
    // CREATE NOTE
    // type is 2 voor notes

    // GET ALL NOTES
    // get all feedback niet alleen notes

    // DELETE NOTE

    // GET NOTE

    //////////////////// PHOTOS
    // CREATE PHOTOS
    public void createPhoto(String name, String path, String subject) {
        // type is 1 voor photos
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PHOTOS, name);
        values.put(KEY_PHOTOS_PATH, path);
        values.put(KEY_PHOTO_SUBJECT, subject);
        values.put(KEY_FB_TYPE, 1);
        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    // GET ALL PHOTOS
    // get all feedback niet alleen photos

    public ArrayList<Photo> readAllPhotosPerSubject(String subject){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Photo> photos = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PHOTOS + " WHERE " + KEY_PHOTO_SUBJECT + " = ?";

        Cursor cursor = db.rawQuery(query, new String[] {subject});
        if (cursor.moveToFirst()) {
            do {
                // MAAK photo OBJECT
                Photo photo = new Photo();
                photo.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                photo.setName((cursor.getString(cursor.getColumnIndex(KEY_PHOTOS))));
                photo.setPath((cursor.getString(cursor.getColumnIndex(KEY_PHOTOS_PATH))));

                // adding to years arraylist
                photos.add(photo);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return photos;
    }

    public boolean subjectItemExists(String subject) {
        SQLiteDatabase db = getReadableDatabase();

        // Query 1 row
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PHOTOS + " WHERE " + KEY_PHOTO_SUBJECT + " = ?", new String[] {subject});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // GET PHOTO

    // GET PHOTO PATH
    public String getPhotoPath(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String path = null;

        String query = "SELECT " + KEY_PHOTOS_PATH + " FROM " + TABLE_PHOTOS + " WHERE " + KEY_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                path = cursor.getString(cursor.getColumnIndex(KEY_PHOTOS_PATH));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return path;
    }

    // DELETE PHOTO
    public void deletePhoto(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PHOTOS, " " + KEY_ID + " = ? ", new String[] {String.valueOf(id)}); // moet die komma in een string?
        db.close();
    }

//    // GET ALL FEEDBACK PER SUBJECT
//    public ArrayList<Feedback> readAllFeedback(String subject){
//        SQLiteDatabase db = getReadableDatabase();
//        ArrayList<Feedback> feedback = new ArrayList<>();
//
//        String query = "SELECT * FROM " + TABLE_PHOTOS+ ", " + TABLE_NOTES + " WHERE " + KEY_SUBJECT_FB + " = " + subject;
//
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                // MAAK feedback OBJECT
//                Subject subject = new Subject();
//                subject.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
//                subject.setName((cursor.getString(cursor.getColumnIndex(KEY_SUBJECT))));
//
//                // adding to years arraylist
//                feedback.add(subject);
//
//            } while (cursor.moveToNext());
//        }
//
//        return feedback;
//    }





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
