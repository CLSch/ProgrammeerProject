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
 * This class contains all functions for the SQLite Database. The database exists of three tables:
 * Years, Subjects and Photos.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String databaseName = "Feedback.db";
    private static final int DATABASE_VERSION = 4;

    // table names
    private static final String TABLE_YEARS = "Years";
    private static final String TABLE_SUBJECTS = "Subjects";
    private static final String TABLE_PHOTOS = "Photos";

    // general key names
    private static final String KEY_ID = "_id";
    private static final String KEY_USER_ID = "UserID";

    // column names for year table
    private static final String KEY_YEARS = "Year";

    // column names for subject table
    private static final String KEY_SUBJECT = "Subject";
    private static final String KEY_SUBJECT_YEAR = "SubjectYear";

    // column names for photo table
    private static final String KEY_PHOTOS = "Photo";
    private static final String KEY_PHOTOS_PATH = "PhotoPath";
    private static final String KEY_PHOTO_SUBJECT = "PhotoSubject";

    // create statements for tables
    private static final String CREATE_TABLE_YEARS = "CREATE TABLE " + TABLE_YEARS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT," + KEY_YEARS + " TEXT)";

    private static final String CREATE_TABLE_SUBJECTS = "CREATE TABLE " + TABLE_SUBJECTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT," + KEY_SUBJECT + " TEXT,"
            + KEY_SUBJECT_YEAR + " TEXT)";

    private static final String CREATE_TABLE_PHOTOS = "CREATE TABLE " + TABLE_PHOTOS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT,"
            + KEY_PHOTOS + " TEXT," + KEY_PHOTO_SUBJECT + " TEXT," + KEY_PHOTOS_PATH + " TEXT)";

    public DBHelper(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    /* Create database when helper object is made and there isn't one already. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_YEARS);
        db.execSQL(CREATE_TABLE_SUBJECTS);
        db.execSQL(CREATE_TABLE_PHOTOS);
    }

    /* Upgrade database when helper object is made and there is one already. */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }

    /* Create Years for in the database */
    public void createYear(String userId) {
        SQLiteDatabase db = getWritableDatabase();

        // add year number for the amount of items in the database
        Long numRows;
        try {
            numRows = DatabaseUtils.queryNumEntries(db, TABLE_YEARS, " " + KEY_USER_ID + " = ?",
                    new String[] {userId});

        } catch (Exception e) {
            numRows = 0L;
        }

        String name = "Year " + String.valueOf(numRows + 1);

        ContentValues values = new ContentValues();
        values.put(KEY_YEARS, name);
        values.put(KEY_USER_ID, userId);
        db.insert(TABLE_YEARS, null, values);
        db.close();
    }

    /* Give all years back for current user. */
    public ArrayList<Year> readAllYears(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Year> years = new ArrayList<>();
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

    /* Delete Year from database for current user. */
    public void deleteYear(int id, String userId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_YEARS, " " + KEY_ID + " = ? AND " + KEY_USER_ID + " = ?",
                new String[] {String.valueOf(id), userId});
        db.close();
    }

    /* Create Subject in database for current user. */
    public void createSubject(String name, String year, String userId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SUBJECT, name);
        values.put(KEY_SUBJECT_YEAR, year);
        values.put(KEY_USER_ID, userId);
        db.insert(TABLE_SUBJECTS, null, values);
        db.close();
    }

    /* Give all subjects per year and for current user back. */
    public ArrayList<Subject> readAllSubjectsPerYear(String year, String userId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> subjects = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_SUBJECT_YEAR
                + " = ? AND " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {year, userId});
        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                subject.setName((cursor.getString(cursor.getColumnIndex(KEY_SUBJECT))));
                subjects.add(subject);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return subjects;
    }

    /* Update the subject name from a particular subject. */
    public void updateSubject(String name, int id, String year, String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SUBJECT, name);

        db.update(TABLE_SUBJECTS, values, KEY_ID + " = ? AND " + KEY_SUBJECT_YEAR + " = ? AND "
                + KEY_USER_ID + " = ?", new String[] {String.valueOf(id), year, userId});
    }

    /* Delete certain subject for current user from database. */
    public void deleteSubject(int id, String userId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SUBJECTS, " " + KEY_ID + " = ? AND " + KEY_USER_ID + " = ?",
                new String[] {String.valueOf(id), userId});
        db.close();
    }

    /* Check if there are year items in the subjects table for the current user. */
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

    /* Create a photo for a certain subject and the current user in database. */
    public void createPhoto(String name, String path, String subject, String userId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PHOTOS, name);
        values.put(KEY_PHOTOS_PATH, path);
        values.put(KEY_PHOTO_SUBJECT, subject);
        values.put(KEY_USER_ID, userId);
        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    /* Give all photos back for certain subject and current user. */
    public ArrayList<Photo> readAllPhotosPerSubject(String subject, String userId){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Photo> photos = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PHOTOS + " WHERE " + KEY_PHOTO_SUBJECT + " = ? AND " + KEY_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[] {subject, userId});
        if (cursor.moveToFirst()) {
            do {
                Photo photo = new Photo();
                photo.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                photo.setName((cursor.getString(cursor.getColumnIndex(KEY_PHOTOS))));
                photo.setPath((cursor.getString(cursor.getColumnIndex(KEY_PHOTOS_PATH))));
                photos.add(photo);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return photos;
    }

    /* Check if there's a subject item in the photo table for current user. */
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

    /* Return the dropboxpath for a particular photo. */
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

    /* Delete a photo from the current user from the database. */
    public void deletePhoto(int id, String userId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PHOTOS, " " + KEY_ID + " = ? AND " + KEY_USER_ID + " = ?",
                new String[] {String.valueOf(id), userId});
        db.close();
    }
}
