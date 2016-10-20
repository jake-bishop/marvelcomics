package com.geekmode.marvelcomics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jake on 10/19/2016.
 */
public class ComicData {
    @SerializedName("offset")
    @Expose
    private int offset;

    @SerializedName("limit")
    @Expose
    private int limit;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("results")
    @Expose
    private List<ComicModel> results;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ComicModel> getResults() {
        return results;
    }

    public void setResults(List<ComicModel> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ComicData{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", total=" + total +
                ", count=" + count +
                ", results=" + results +
                '}';
    }
}
