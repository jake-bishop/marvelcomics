package com.geekmode.marvelcomics.model;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.google.gson.annotations.Expose;

public class Character {


//    id: 1009175,
//    name: "Beast",
//    description: "",
//    modified: "2014-01-13T14:48:32-0500",
//    thumbnail: {
//        path: "http://i.annihil.us/u/prod/marvel/i/mg/2/80/511a79a0451a3",
//                extension: "jpg"
//    },
//    resourceURI: "http://gateway.marvel.com/v1/public/characters/1009175",

    @Expose private long id;
    @Expose private String name;
    @Expose private String description;
    @Expose private Thumbnail thumbnail;
    @Expose private String ResourceURI;

    public Character(Long id, String name, String description, String thumbPath, String thumbExtension){
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = new Thumbnail();
        this.thumbnail.setPath(thumbPath);
        this.thumbnail.setExtension(thumbExtension);
    };

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getResourceURI() {
        return ResourceURI;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setThumbnail(final Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ContentValues toContentValues(){
        final ContentValues values = new ContentValues();
        values.put(Entity._ID, id);
        values.put(Entity.COLUMN_NAME, name);
        values.put(Entity.COLUMN_DESCRIPTION, description);
        values.put(Entity.COLUMN_THUMBNAIL_PATH, thumbnail.getPath());
        values.put(Entity.COLUMN_THUMBNAIL_EXTENSION, thumbnail.getExtension());
        return values;
    }

    public static class Entity implements BaseColumns {
        public static final String TABLE_NAME = "fave_characters";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_THUMBNAIL_PATH = "thumbnail_path";
        public static final String COLUMN_THUMBNAIL_EXTENSION = "thumbnail_extension";
    }
}
