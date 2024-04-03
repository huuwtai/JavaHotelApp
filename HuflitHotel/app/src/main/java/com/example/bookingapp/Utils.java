package com.example.bookingapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static final String SHARE_PREFERENCES_APP = "share_preferences_app";
    public static final String KEY_ACCOUNT = "key_account";
    public static final String KEY_IS_LOGIN = "key_is_login";
    public static final String KEY_USER = "key_user";
    public static final String KEY_USER_PROFILE = "key_user_profile";
    //User
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_NAME ="username";
    public static final String COLUMN_USER_PASSWORD ="password";
    public static final String COLUMN_USER_EMAIL ="email";
    public static final String COLUMN_USER_PHONE ="phone";
    // LUU Y KIEU SEX NAY LA STRING VI TABLE LA STRING (KIEU DUNG LA INT)
    public static final String COLUMN_USER_SEX ="sex";
    public static final String COLUMN_USER_AVATAR ="avatar";
    //Room
    public static final String DATABASE_NAME = "db-room";
    public static final String TABLE_ROOM = "room";
    public static final String COLUMN_ROOM_ID = "roomid";
    public static final String COLUMN_ROOM_NAME = "roomname";
    public static final String COLUMN_ROOM_AVATAR = "roomavatar";
    public static final String COLUMN_ROOM_typeid = "typeid";
    //Room Type
    public static final String TABLE_ROOMTYPE = "room_type";
    public static final String COLUMN_ROOMTYPE_ID = "id";
    public static final String COLUMN_ROOMTYPE_NAME = "name";
    // function
    public static Bitmap convertToBitmapFromAssets(Context context, String nameImage)
    {
        AssetManager assetManager= context.getAssets();
        try {
            InputStream inputStream = assetManager.open("images/"+nameImage);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
