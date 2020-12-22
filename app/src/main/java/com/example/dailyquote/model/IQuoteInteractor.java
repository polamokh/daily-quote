package com.example.dailyquote.model;

public interface IQuoteInteractor {
    interface IQuoteListener {
        void onQuoteLoaded(Quote quote);

        void onQuoteFailure(Throwable t);
    }

    void getQuote(String category, IQuoteListener listener);
}
