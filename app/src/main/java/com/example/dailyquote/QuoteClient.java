package com.example.dailyquote;

import com.example.dailyquote.model.IQuoteApi;
import com.example.dailyquote.model.QuoteContract;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class QuoteClient {
    private static QuoteClient instance;
    private IQuoteApi quoteApi;

    private QuoteClient() {
        quoteApi = new Retrofit.Builder()
                .baseUrl(QuoteContract.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(IQuoteApi.class);
    }

    public static QuoteClient getInstance() {
        if (instance == null)
            instance = new QuoteClient();
        return instance;
    }

    public Call<String> getDailyQuote(String language, String category) {
        return quoteApi.getDailyQuote(language, category);
    }
}
