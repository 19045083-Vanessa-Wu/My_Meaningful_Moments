package sg.edu.rp.c346.id19045083.mymeaningfulmoments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyMeaningfulMoments.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOMENTS = "MeaningfulMoments";
    private static final String COLUMN_ID = "id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TABLE_MOMENTS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, location TEXT, date TEXT, description TEXT, stars INTEGER)";
        db.execSQL(createTableSQL);
        Log.i("INFO", "Created Tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOMENTS);
        onCreate(db);
    }

    public long insertMoment (String Title, String Location, String date, String Description, int Stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", Title);
        values.put("location", Location);
        values.put("date", date);
        values.put("description", Description);
        values.put("stars", Stars);
        long result = db.insert(TABLE_MOMENTS, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result); //ID returned, shouldn't be -1
        if (result == -1) {
            Log.d("DBHelper", "Insert Failed");
        }
        return result;
    }

    public ArrayList<Moments> getAllMoments() {
        ArrayList<Moments> foods = new ArrayList<Moments>();

        String selectQuery = "SELECT " + COLUMN_ID + ", title, location, date, description, stars " +
                "FROM " + TABLE_MOMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String date = cursor.getString(3);
                String description = cursor.getString(4);
                int stars = cursor.getInt(5);
                Moments info = new Moments(id, name, location, date, description, stars);
                foods.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foods;
    }

    public int updateMoment(Moments input) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", input.getTitle());
        values.put("location", input.getLocation());
        values.put("date", input.getDate());
        values.put("description", input.getDescription());
        values.put("stars", input.getStars());
        String condition = COLUMN_ID + "=?";
        String[] args = {String.valueOf(input.getId())};
        int result = db.update(TABLE_MOMENTS, values, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Update Failed");
        }
        db.close();
        return result;
    }

    public int deleteMoment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "=?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_MOMENTS, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Delete Failed");
        }
        db.close();
        return result;
    }

    public ArrayList<Moments> getAllMomentWithStars(int stars) {
        ArrayList<Moments> ALmoment = new ArrayList<Moments>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, "title, location, date, description, stars"};
        String condition = "stars == ?";
        String[] args = {String.valueOf(stars)};
        Cursor cursor = db.query(TABLE_MOMENTS, columns, condition, args, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String date = cursor.getString(3);
                String description = cursor.getString(4);
                int Stars = cursor.getInt(5);
                Moments info = new Moments(id, name, location, date, description, Stars);
                ALmoment.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ALmoment;
    }

} //DBHelper class
