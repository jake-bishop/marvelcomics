package com.geekmode.marvelcomics.services;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.geekmode.marvelcomics.model.Character;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ObservableDatabase {

    public static final String QUERY_CHARACTERS = "SELECT * FROM " + Character.Entity.TABLE_NAME;
    private final BriteDatabase briteDatabase;

    public ObservableDatabase(final Context context) {
        final SqlBrite instance = new SqlBrite.Builder().logger(message -> Log.d(ObservableDatabase.class.getSimpleName(), message)).build();
        briteDatabase = instance.wrapDatabaseHelper(new ComicDatabaseOpenHelper(context), Schedulers.io());
        briteDatabase.setLoggingEnabled(true);
    }

    public void insertCharacter(final ContentValues contentValues) {
        briteDatabase.insert(Character.Entity.TABLE_NAME, contentValues);
    }

    public Observable<List<Character>> observeCharacters() {
        return briteDatabase.createQuery(Character.Entity.TABLE_NAME, QUERY_CHARACTERS)
                .map(query -> {
                    final List<Character> characterList = new ArrayList<>();

                    try (final Cursor cursor = query.run()) {
                        if (cursor != null) {

                            while (cursor.moveToNext()) {
                                final Long id = cursor.getLong(cursor.getColumnIndexOrThrow(Character.Entity._ID));
                                final String name = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_NAME));
                                final String thumbPath = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_THUMBNAIL_PATH));
                                final String thumbExtension = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_THUMBNAIL_EXTENSION));
                                final String description = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_DESCRIPTION));
                                final Character character = new Character(id, name, description, thumbPath, thumbExtension);
                                characterList.add(character);
                            }
                        }
                    }

                    return characterList;
                });
    }

    public Observable<Character> observeCharacter(final long characterId) {
        return briteDatabase.createQuery(Character.Entity.TABLE_NAME, QUERY_CHARACTERS + " WHERE " + Character.Entity._ID + " = " + characterId)
                .map(query -> {
                    final List<Character> characterList = new ArrayList<>();

                    try (final Cursor cursor = query.run()) {
                        if (cursor != null) {

                            while (cursor.moveToNext()) {
                                final Long id = cursor.getLong(cursor.getColumnIndexOrThrow(Character.Entity._ID));
                                final String name = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_NAME));
                                final String thumbPath = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_THUMBNAIL_PATH));
                                final String thumbExtension = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_THUMBNAIL_EXTENSION));
                                final String description = cursor.getString(cursor.getColumnIndexOrThrow(Character.Entity.COLUMN_DESCRIPTION));
                                final Character character = new Character(id, name, description, thumbPath, thumbExtension);
                                characterList.add(character);
                            }
                        }
                    }

                    return characterList;
                })
                .filter((characterList) -> characterList.size() == 1)
                .map((characterList) -> characterList.get(0));
    }
}