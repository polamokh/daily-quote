package com.example.dailyquote.view;

import com.example.dailyquote.model.Quote;

public interface IQuoteView {
    void setQuote(Quote quote);

    void onError(Throwable t);
}
