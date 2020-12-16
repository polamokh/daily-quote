package com.example.dailyquote.model;

public interface IQuote {
    interface OnFinishedListener {
        void onFinished(Quote quote);
    }

    void getQuote(OnFinishedListener listener);
}
