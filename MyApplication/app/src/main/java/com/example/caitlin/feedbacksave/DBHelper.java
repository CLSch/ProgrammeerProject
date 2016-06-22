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
import java.util.ArrayList;

/**
 * In deze class staan alle functies voor de SQLite Database.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String databaseName = "Feedback.db";
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
    private static final String KEY_USER_ID = "UserID";

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
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT," + KEY_YEARS + " TEXT)";

    private static final String CREATE_TABLE_SUBJECTS = "CREATE TABLE " + TABLE_SUBJECTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT," + KEY_SUBJECT + " TEXT,"
            + KEY_SUBJECT_YEAR + " TEXT)";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT,"
            + KEY_NOTES + " TEXT," + KEY_FB_TYPE + " INTEGER)";

    private static final String CREATE_TABLE_PHOTOS = "CREATE TABLE " + TABLE_PHOTOS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT,"
            + KEY_PHOTOS + " TEXT," + KEY_PHOTO_SUBJECT + " TEXT," + KEY_PHOTOS_PATH + " TEXT,"
            + KEY_FB_TYPE + " INTEGER)";

    public DBHelper(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);
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
    public void createYear(String userId) {
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
        values.put(KEY_USER_ID, userId);
        db.insert(TABLE_YEARS, null, values);
        db.close();
    }

    // GET ALL YEARS
    public ArrayList<Year> readAllYears(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Year> years = new ArrayList<>();
        //String query = "SELECT " + KEY_ID + ", " + KEY_YEARS + " FROM " + TABLE_YEARS;
        String query = "SELECT * FROM " + TABLE_YEARS + " WHERE " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {userId});
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

    // stackoverflow
    public boolean subjectYearItemExists(String year, String userId) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_SUBJECT_YEAR
                + " = ? AND " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query , new String[] {year, userId});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // niet maar eentje toch?

    ////////////////////// SUBJECTS
    // CREATE SUBJECT
    public void createSubject(String name, String year, String userId) {
        Log.d("in create year", year);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SUBJECT, name);
        values.put(KEY_SUBJECT_YEAR, year);
        values.put(KEY_USER_ID, userId);
        db.insert(TABLE_SUBJECTS, null, values);
        db.close();
    }

    // GET SUBJECT
    // is dit echt nodig???

    // GET ALL SUBJECTS
    public ArrayList<Subject> readAllSubjectsPerYear(String year, String userId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> subjects = new ArrayList<>();

//        String query = "SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_SUBJECT_YEAR + " = ?", year;
        String query = "SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_SUBJECT_YEAR
                + " = ? AND " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {year, userId});
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

    // UPDATE ALL SUBJECTS
    // je kan dit ook doen door het item te verwijderen en opnieuw aan te maken
    public void updateSubject(String name, int id, String year, String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SUBJECT, name);

        db.update(TABLE_SUBJECTS, values, KEY_ID + " = ? AND " + KEY_SUBJECT_YEAR + " = ? AND "
                + KEY_USER_ID + " = ?", new String[] {String.valueOf(id), year, userId});
    }

    // TODO delete alle feedback die bij de subject hoort
    // DELETE SUBJECT
    // kan ook met de naam gedaan worden als dat handiger is
    public void deleteSubject(int id, String userId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SUBJECTS, " " + KEY_ID + " = ? AND " + KEY_USER_ID + " = ?",
                new String[] {String.valueOf(id), userId});
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
    public void createPhoto(String name, String path, String subject, String userId) {
        // type is 1 voor photos
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PHOTOS, name);
        values.put(KEY_PHOTOS_PATH, path);
        values.put(KEY_PHOTO_SUBJECT, subject);
        values.put(KEY_FB_TYPE, 1);
        values.put(KEY_USER_ID, userId);
        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    // GET ALL PHOTOS
    // get all feedback niet alleen photos

    public ArrayList<Photo> readAllPhotosPerSubject(String subject, String userId){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Photo> photos = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PHOTOS + " WHERE " + KEY_PHOTO_SUBJECT + " = ? AND " + KEY_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[] {subject, userId});
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

    public boolean subjectItemExists(String subject, String userId) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PHOTOS + " WHERE " + KEY_PHOTO_SUBJECT + " = ? AND " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {subject, userId});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // GET PHOTO

    // GET PHOTO PATH
    public String getPhotoPath(int id, String userId) {
        SQLiteDatabase db = getReadableDatabase();
        String path = null;

        String query = "SELECT " + KEY_PHOTOS_PATH + " FROM " + TABLE_PHOTOS + " WHERE " +
                KEY_ID + " = ? AND " + KEY_USER_ID + " = ?" ;
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id), userId});
        if (cursor.moveToFirst()) {
            do {
                path = cursor.getString(cursor.getColumnIndex(KEY_PHOTOS_PATH));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return path;
    }

    // DELETE PHOTO
    public void deletePhoto(int id, String userId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PHOTOS, " " + KEY_ID + " = ? AND " + KEY_USER_ID + " = ?",
                new String[] {String.valueOf(id), userId}); // moet die komma in een string?
        db.close();
    }
}
