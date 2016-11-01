package com.geekmode.marvelcomics.services;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.geekmode.marvelcomics.model.Character;

public class ComicDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "marvel-comics.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_COMICS_TABLE =
            "CREATE TABLE " + Character.Entity.TABLE_NAME
                    + " ("
                    + Character.Entity._ID + " INTEGER PRIMARY KEY,"
                    + Character.Entity.COLUMN_NAME + " TEXT,"
                    + Character.Entity.COLUMN_DESCRIPTION + " TEXT,"
                    + Character.Entity.COLUMN_THUMBNAIL_PATH + " TEXT,"
                    + Character.Entity.COLUMN_THUMBNAIL_EXTENSION + " TEXT"
                    + ")";

    public ComicDatabaseOpenHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(CREATE_COMICS_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }

    @Override
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
