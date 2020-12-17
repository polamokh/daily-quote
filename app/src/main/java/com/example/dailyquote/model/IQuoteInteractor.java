package com.example.dailyquote.model;

public interface IQuoteInteractor {
    interface OnFinishedListener {
        void onFinished(Quote quote);
    }

    void getQuote(OnFinishedListener listener);
}
