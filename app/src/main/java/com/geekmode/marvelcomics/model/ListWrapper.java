package com.geekmode.marvelcomics.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ListWrapper<T> {

    @Expose
    private int offset;
    @Expose
    private int limit;
    @Expose
    private int total;
    @Expose
    private int count;
    @Expose
    private List<T> results;

    public int getCount() {
        return count;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public List<T> getResults() {
        return results;
    }

    public int getTotal() {
        return total;
    }

    public void setResults(final List<T> results) {
        this.results = results;
    }
}
