package com.quochung.covid20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TinTuc {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalresults")
    @Expose
    private int totalresults;

    @SerializedName("articles")
    @Expose
    private List<Article> article;

    public String getStatus() {
        return status;
    }

    public int getTotalresults() {
        return totalresults;
    }

    public List<Article> getArticle() {
        return article;
    }
}

