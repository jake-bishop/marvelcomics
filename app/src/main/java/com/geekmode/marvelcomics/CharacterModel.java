package com.geekmode.marvelcomics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Jake on 9/18/2016.
 */
public class CharacterModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("modified")
    @Expose
    private Date modified;

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getModified() {
        return modified;
    }

    public String getThumbnailPath() {
        return this.thumbnail.getPath() + "." + this.thumbnail.getExtension();
    }

    @Override
    public String toString() {
        return "CharacterModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", modified=" + modified +
                ", thumbnail=" + thumbnail +
                '}';
    }

    private class Thumbnail {
        @SerializedName("path")
        @Expose
        private String path;

        @SerializedName("extension")
        @Expose
        private String extension;

        public String getPath() {
            return path;
        }

        public String getExtension() {
            return extension;
        }

        @Override
        public String toString() {
            return "Thumbnail{" +
                    "path='" + path + '\'' +
                    ", extension='" + extension + '\'' +
                    '}';
        }
    }
}
