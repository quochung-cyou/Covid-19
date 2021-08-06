package com.quochung.covid20.api;

import com.quochung.covid20.models.TinTuc;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("everything")
    Call<TinTuc> getNews(

        @Query("q") String q,
        @Query("sortBy") String sortby,
        @Query("apiKey") String apiKey
    );


}
