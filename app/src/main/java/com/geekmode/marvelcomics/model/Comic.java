package com.geekmode.marvelcomics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comic {
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("etag")
    @Expose
    private String etag;

    @SerializedName("attributionText")
    @Expose
    private String attributionText;

    @SerializedName("data")
    @Expose
    private ComicData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public ComicData getData() {
        return data;
    }

    public void setData(ComicData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", etag='" + etag + '\'' +
                ", attributionText='" + attributionText + '\'' +
                ", data=" + data +
                '}';
    }
}
