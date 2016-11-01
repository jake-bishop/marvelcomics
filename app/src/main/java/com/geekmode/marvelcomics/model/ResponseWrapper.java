package com.geekmode.marvelcomics.model;

import com.google.gson.annotations.Expose;

public class ResponseWrapper<T> {

    @Expose
    private int code;
    @Expose
    private String status;
    @Expose
    private String copyright;
    @Expose
    private String attributionText;
    @Expose
    private String attributionHTML;
    @Expose
    private String etag;
    @Expose
    private ListWrapper<T> data;

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public int getCode() {
        return code;
    }

    public String getCopyright() {
        return copyright;
    }

    public ListWrapper<T> getData() {
        return data;
    }

    public String getEtag() {
        return etag;
    }

    public String getStatus() {
        return status;
    }

    public void setData(ListWrapper<T> data) {
        this.data = data;
    }
}
