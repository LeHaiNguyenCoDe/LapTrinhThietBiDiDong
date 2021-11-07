package com.example.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * hàm kết nối database và xử lý databas Thêm user
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context ) {
        super(context, com.example.mycontacts.DatabaseOptions.DB_NAME, null, com.example.mycontacts.DatabaseOptions.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
        db.execSQL(com.example.mycontacts.DatabaseOptions.CREATE_USERS_TABLE_);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + com.example.mycontacts.DatabaseOptions.USERS_TABLE);
        // Create tables again
        onCreate(db);
    }

    public com.example.mycontacts.User queryUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        com.example.mycontacts.User user = null;


        Cursor cursor = db.query(com.example.mycontacts.DatabaseOptions.USERS_TABLE, new String[]{com.example.mycontacts.DatabaseOptions.ID,
                        com.example.mycontacts.DatabaseOptions.EMAIL, com.example.mycontacts.DatabaseOptions.PASSWORD}, com.example.mycontacts.DatabaseOptions.EMAIL + "=? and " + com.example.mycontacts.DatabaseOptions.PASSWORD + "=?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new com.example.mycontacts.User(cursor.getString(1), cursor.getString(2));
        }
        // return user
        return user;
    }

    public void addUser(com.example.mycontacts.User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(com.example.mycontacts.DatabaseOptions.EMAIL, user.getEmail());
        values.put(com.example.mycontacts.DatabaseOptions.PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(com.example.mycontacts.DatabaseOptions.USERS_TABLE, null, values);
        db.close(); // Closing database connection

    }








}
