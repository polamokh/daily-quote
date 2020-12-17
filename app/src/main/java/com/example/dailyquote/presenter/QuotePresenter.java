package com.example.dailyquote.presenter;

import com.example.dailyquote.model.IQuoteInteractor;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteInteractor;
import com.example.dailyquote.view.IQuoteView;

public class QuotePresenter implements IQuotePresenter, IQuoteInteractor.OnFinishedListener {
    private IQuoteView quoteView;
    private QuoteInteractor quoteInteractor;

    public QuotePresenter(IQuoteView quoteView, QuoteInteractor quoteInteractor) {
        this.quoteView = quoteView;
        this.quoteInteractor = quoteInteractor;
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
        quoteInteractor.getQuote(this);
    }
}
