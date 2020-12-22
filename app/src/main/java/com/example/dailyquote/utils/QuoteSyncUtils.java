package com.example.dailyquote.utils;

import android.content.Context;

import com.example.dailyquote.model.IQuoteInteractor;
import com.example.dailyquote.model.QuoteSharedPreference;
import com.example.dailyquote.sync.QuoteSyncNetwork;

public class QuoteSyncUtils {

    public static void initialize(Context context, String language, String category,
                                  IQuoteInteractor.IQuoteListener listener) {
        if (QuoteSharedPreference.getElapsedTimeSinceLastSync(context) > 0)
            getRemoteQuote(context, language, category, listener);
        else
            getLocalQuote(context, listener);
    }

    private static void getRemoteQuote(Context context, String language, String category,
                                       IQuoteInteractor.IQuoteListener listener) {
        QuoteSyncNetwork.syncQuote(context, language, category, listener);
    }

    private static void getLocalQuote(Context context, IQuoteInteractor.IQuoteListener listener) {
        listener.onQuoteLoaded(QuoteSharedPreference.loadQuoteData(context));
    }
}
