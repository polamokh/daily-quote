package com.example.dailyquote.presenter;

import com.example.dailyquote.model.IQuote;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.view.IQuoteView;

public class QuotePresenter implements IQuotePresenter, IQuote.OnFinishedListener {
    private IQuoteView quoteView;
    private Quote quote;

    public QuotePresenter(IQuoteView quoteView, Quote quote) {
        this.quoteView = quoteView;
        this.quote = quote;
    }

    @Override
    public void onFinished(Quote quote) {
        quoteView.setQuote(quote);
    }

    @Override
    public void onDestroy() {
        quoteView = null;
    }

    @Override
    public void onResume() {
        quote.getQuote(this);
    }
}
