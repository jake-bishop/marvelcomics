package com.geekmode.marvelcomics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComicModel {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("issueNumber")
    @Expose
    private Double issueNumber;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Double issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailPath() {
        return this.thumbnail.getPath() + "." + this.thumbnail.getExtension();
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "ComicModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", issueNumber=" + issueNumber +
                ", description='" + description + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
