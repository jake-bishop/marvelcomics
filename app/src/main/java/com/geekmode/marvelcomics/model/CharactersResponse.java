package com.geekmode.marvelcomics.model;

import com.geekmode.marvelcomics.model.CharacterModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharactersResponse {
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
    private CharacterData data;

    public CharacterModel getFirstCharacter() {
        if(data != null && data.getResults() != null && data.getResults().size() > 0) {
            return data.getResults().get(0);
        }
        return null;
    }

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

    public CharacterData getData() {
        return data;
    }

    public void setData(CharacterData data) {
        this.data = data;
    }

    private class CharacterData {
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

    @Override
    public String toString() {
        return "CharactersResponse{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", etag='" + etag + '\'' +
                ", data=" + data +
                '}';
    }
}
