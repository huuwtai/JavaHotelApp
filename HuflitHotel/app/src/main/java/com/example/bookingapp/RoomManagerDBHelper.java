package com.example.bookingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RoomManagerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = com.example.bookingapp.Utils.DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;

    public RoomManagerDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_ROOMTYPE_TABLE = "CREATE TABLE " + com.example.bookingapp.Utils.TABLE_ROOMTYPE + "("
                + com.example.bookingapp.Utils.COLUMN_ROOMTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + com.example.bookingapp.Utils.COLUMN_ROOMTYPE_NAME + " TEXT"
                +")";
        String CREATE_USER_TABLE = "CREATE TABLE " + com.example.bookingapp.Utils.TABLE_USER + "("
                + com.example.bookingapp.Utils.COLUMN_USER_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + com.example.bookingapp.Utils.COLUMN_USER_PASSWORD + " TEXT, "
                + com.example.bookingapp.Utils.COLUMN_USER_AVATAR + " TEXT,"
                + com.example.bookingapp.Utils.COLUMN_USER_EMAIL + " TEXT,"
                + com.example.bookingapp.Utils.COLUMN_USER_PHONE + " TEXT,"
                + com.example.bookingapp.Utils.COLUMN_USER_SEX + " TEXT"
                +")";
        String CREATE_ROOM_TABLE = "CREATE TABLE " + com.example.bookingapp.Utils.TABLE_ROOM + "("
                + com.example.bookingapp.Utils.COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + com.example.bookingapp.Utils.COLUMN_ROOM_NAME + " TEXT,"
                + com.example.bookingapp.Utils.COLUMN_ROOM_AVATAR + " TEXT,"
                + com.example.bookingapp.Utils.COLUMN_ROOM_typeid + " TEXT"
                +")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ROOMTYPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_ROOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + com.example.bookingapp.Utils.TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + com.example.bookingapp.Utils.TABLE_ROOM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + com.example.bookingapp.Utils.TABLE_ROOMTYPE);
        onCreate(sqLiteDatabase);
    }
}
