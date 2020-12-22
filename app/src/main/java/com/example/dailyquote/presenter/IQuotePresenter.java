package com.example.dailyquote.presenter;

public interface IQuotePresenter {
    void onDestroy();

    void onGetQuote(String category);
}
