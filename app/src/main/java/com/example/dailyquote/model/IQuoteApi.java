package com.example.dailyquote.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IQuoteApi {
    @GET("qod")
    Call<String> getDailyQuote(@Query("language") String language,
                               @Query("category") String category);
}
