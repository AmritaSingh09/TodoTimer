package com.example.todotimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

    Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todotimerdb";
    public static final String TABLE_NAME = "timertable";


    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME+" ( ID TEXT PRIMARY KEY ," +
                " TITLE TEXT ," +
                " DESCRIPTION TEXT," +
                " DURATION  INTEGER," +
                " TIME_LEFT TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DATABASE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void addNewTodo(TodoModal modal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("TITLE",modal.title);
        contentValues.put("DESCRIPTION",modal.description);
        contentValues.put("DURATION",modal.duration);
        contentValues.put("TIME_LEFT",modal.time_left);
        contentValues.put("ID",modal.id);

        db.insert(TABLE_NAME,null,contentValues);
        db.close();
        /*
        String query = "INSERT INTO "+ DATABASE_NAME+" VALUES ( "+ String.valueOf(System.currentTimeMillis())+" , "
                + modal.title + " , "
                + modal.description + " , "
                + modal.duration + " , "
                + modal.time_left + " , "
                + modal.duration
                + ")";

         */
    }

    public List<TodoModal> getAllTodos(){
        List<TodoModal> modalList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        if (cursor.moveToFirst()){
            do {
                modalList.add(new TodoModal(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(0)));
            }while (cursor.moveToNext());

        }
        cursor.close();
        return modalList;
    }

    public boolean removeOneTodo(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{id});
        db.close();
        return true;
    }
    //on running on stop on pause and on resume status

    public void updateDataToDatabase(TodoModal modal) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*String query = "UPDATE INTO "+ DATABASE_NAME+" VALUES ( "+ String.valueOf(System.currentTimeMillis())+" , "
                + modal.title + " , "
                + modal.description + " , "
                + modal.duration + " , "
                + modal.time_left + " , "
                + modal.duration
                + ") WHERE ID = "+modal.id;*/

        ContentValues contentValues = new ContentValues();
        /*contentValues.put("TITLE",modal.title);
        contentValues.put("DESCRIPTION",modal.description);
        contentValues.put("DURATION",modal.duration);
        contentValues.put("ID",modal.id);*/
        contentValues.put("TIME_LEFT",modal.time_left);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{modal.id});
        db.close();
        //database.execSQL(query);
    }

}
