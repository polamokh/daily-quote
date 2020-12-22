package com.example.dailyquote.model;

import android.content.Context;

import com.example.dailyquote.utils.QuoteSyncUtils;

public class QuoteInteractor implements IQuoteInteractor {
    private Context context;

    public QuoteInteractor(Context context) {
        this.context = context;
    }

    @Override
    public void getQuote(String category, IQuoteListener listener) {
        QuoteSyncUtils.initialize(context, "en", category, listener);
    }
}
