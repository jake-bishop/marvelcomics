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
