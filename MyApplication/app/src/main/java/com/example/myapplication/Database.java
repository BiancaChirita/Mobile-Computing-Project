package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USER = "User";
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String GAME = "Game";
    private static final String GAME_ID = "game_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    private static final String ACHIEVEMENT = "Achievement";
    private static final String ACHIEVEMENT_ID = "achievement_id";
    private static final String ACHIEVEMENT_NAME = "name";
    private static final String ACHIEVEMENT_DESCRIPTION = "description";

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_USER_CREATE =
            " CREATE TABLE " + USER +"(" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EMAIL + " TEXT NOT NULL, " +
                    USERNAME + " TEXT NOT NULL, " +
                    PASSWORD + " TEXT NOT NULL)";

    private static final String TABLE_GAME_CREATE =
            "CREATE TABLE " + GAME + "(" +
                    GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT NOT NULL, " +
                    DESCRIPTION + " TEXT NOT NULL, " +
                    IMAGE + " TEXT, " +
                    USER_ID + " INTEGER NOT NULL, " +
                    " FOREIGN KEY(" + USER_ID + ")REFERENCES " + USER + "(" + USER_ID + ")" + ")";

    private static final String TABLE_ACHIEVEMENT_CREATE =
            "CREATE TABLE " + ACHIEVEMENT + "(" +
                    ACHIEVEMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ACHIEVEMENT_NAME + " TEXT NOT NULL, " +
                    ACHIEVEMENT_DESCRIPTION + " TEXT NOT NULL, " +
                    GAME_ID + " INTEGER NOT NULL, " +
                    " FOREIGN KEY(" + GAME_ID + ")REFERENCES " + GAME + "(" + GAME_ID + ")" + ")";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_GAME_CREATE);
        db.execSQL(TABLE_ACHIEVEMENT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(" DROP TABLE IF EXISTS " + USER);
        db.execSQL(TABLE_USER_CREATE);

        db.execSQL(" DROP TABLE IF EXISTS " + GAME);
        db.execSQL(TABLE_GAME_CREATE);

        db.execSQL(" DROP TABLE IF EXISTS " + ACHIEVEMENT);
        db.execSQL(TABLE_ACHIEVEMENT_CREATE);
    }


    public Boolean registerUser(String email, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, email);
        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        long in = db.insert(USER, null, contentValues);
        if(in == 1)
            return false;
        else {
            return true;
        }
    }

    public Boolean registerGame(String name, String description, String image, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(IMAGE, image);
        contentValues.put(USER_ID, id);
        long in = db.insert(GAME, null, contentValues);
        if(in == 1)
            return false;
        else {
            return true;
        }
    }

    public Boolean registerAchievement(String name, String description, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACHIEVEMENT_NAME, name);
        contentValues.put(ACHIEVEMENT_DESCRIPTION, description);
        contentValues.put(GAME_ID, id);
        long in = db.insert(ACHIEVEMENT, null, contentValues);
        if(in == 1)
            return false;
        else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(" SELECT * FROM " + USER + " WHERE "+ EMAIL + " = ?", new String[]{email});
            if (cursor.getCount() > 0)
                return false;
            else
                return true;
        }catch (Exception e){
            throw e;
        }
    }

    public boolean checkEmailPassword(String email, String password){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM "+ USER +" WHERE "+ EMAIL + " = ? AND " + PASSWORD + " = ?";
        Cursor cursor = database.rawQuery( query, new String[]{email, password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getUsername(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + USER + " WHERE " + EMAIL + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{email});

        if(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex(USERNAME));
            return username;
        }
        return null;
    }

    public int getUserId(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = " SELECT " + USER_ID + " FROM " + USER + " WHERE " + EMAIL + " =? ";
        Cursor cursor = database.rawQuery(query, new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return -1;
    }

    public int getGameId(int userId){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = " SELECT " + GAME_ID + " FROM " + GAME + " WHERE " + USER_ID + " =" + userId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return -1;
    }

    public Bitmap getUserImage(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM " + GAME + " WHERE " + GAME_ID + " =? ";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        Bitmap image ;

        if(cursor.moveToNext()){
            String imageBase64 = cursor.getString(cursor.getColumnIndex(IMAGE));
            if (imageBase64 == null){
                return null;
            }else{
                byte[] imageByte = Base64.decode(imageBase64, Base64.DEFAULT);
                image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                return image;
            }
        }
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        return null;
    }
}
