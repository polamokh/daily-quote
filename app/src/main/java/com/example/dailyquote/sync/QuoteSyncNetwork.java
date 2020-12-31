package com.example.dailyquote.sync;

import android.content.Context;

import com.example.dailyquote.QuoteClient;
import com.example.dailyquote.model.IQuoteInteractor;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteSharedPreference;
import com.example.dailyquote.utils.QuoteJsonUtils;
import com.example.dailyquote.utils.QuoteNotificationUtils;
import com.example.dailyquote.utils.QuoteSyncUtils;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteSyncNetwork {

    public static void syncQuote(Context context, String language, String category,
                                 IQuoteInteractor.IQuoteListener listener) {
        QuoteClient.getInstance().getDailyQuote(language, category)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Quote quote = saveQuoteLocal(context, response);

                            if (!QuoteSharedPreference.isJobScheduled(context))
                                QuoteSyncUtils.scheduleSyncJob(context);

                            listener.onQuoteLoaded(quote);
                        } else
                            listener.onQuoteFailure(new Exception(response.message()));
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        listener.onQuoteFailure(t);
                    }
                });
    }

    public static boolean syncQuote(Context context, String language, String category) {
        try {
            Response<String> response = QuoteClient.getInstance().getDailyQuote(language, category)
                    .execute();

            if (response.isSuccessful()) {
                Quote quote = saveQuoteLocal(context, response);

                QuoteNotificationUtils.notifyUser(context, quote);

                return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static Quote saveQuoteLocal(Context context, Response<String> response) {
        Quote quote = QuoteJsonUtils.extractFeatureFromJson(response.body());

        QuoteSharedPreference.saveQuoteData(context,
                Objects.requireNonNull(quote));

        return quote;
    }
}
