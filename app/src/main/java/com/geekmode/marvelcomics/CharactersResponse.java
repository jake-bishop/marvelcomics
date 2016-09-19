package com.geekmode.marvelcomics;

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

    public String getStatus() {
        return status;
    }

    public String getEtag() {
        return etag;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public CharacterData getData() {
        return data;
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

        public int getLimit() {
            return limit;
        }

        public int getTotal() {
            return total;
        }

        public int getCount() {
            return count;
        }

        public List<CharacterModel> getResults() {
            return results;
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
