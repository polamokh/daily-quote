package me.polamokh.dailyquote.network;

import com.squareup.moshi.Moshi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface QuoteService {
    @GET("qod")
    Call<ResponseDTO> getDailyQuote(@Query("language") String language,
                                    @Query("category") String category);
}

public class QuoteClient {

    public static final String BASE_URL = "https://quotes.rest/";
    private static QuoteClient instance;
    private final QuoteService quoteService;

    private QuoteClient() {
        Moshi moshi = new Moshi.Builder().build();

        quoteService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(QuoteService.class);
    }

    public static QuoteClient getInstance() {
        if (instance == null)
            instance = new QuoteClient();
        return instance;
    }

    public Call<ResponseDTO> getDailyQuote(String language, String category) {
        return quoteService.getDailyQuote(language, category);
    }

    public enum CATEGORIES {
        inspire, management, sports, life, funny, love, art, students
    }
}
