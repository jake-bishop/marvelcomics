package com.geekmode.marvelcomics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterData {
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
    private List<CharacterModel> results;

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

    public List<CharacterModel> getResults() {
        return results;
    }

    public void setResults(List<CharacterModel> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CharacterData{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", total=" + total +
                ", count=" + count +
                ", results=" + results +
                '}';
    }
}
