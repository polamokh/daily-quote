package com.example.dailyquote.presenter;

import com.example.dailyquote.model.IQuoteInteractor;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteInteractor;
import com.example.dailyquote.view.IQuoteView;

public class QuotePresenter implements IQuotePresenter, IQuoteInteractor.IQuoteListener {
    private IQuoteView quoteView;
    private QuoteInteractor quoteInteractor;

    public QuotePresenter(IQuoteView quoteView, QuoteInteractor quoteInteractor) {
        this.quoteView = quoteView;
        this.quoteInteractor = quoteInteractor;
    }

    @Override
    public void onDestroy() {
        quoteView = null;
    }

    @Override
    public void onGetQuote(String category) {
        quoteInteractor.getQuote(category, this);
    }

    @Override
    public void onQuoteLoaded(Quote quote) {
        quoteView.setQuote(quote);
    }

    @Override
    public void onQuoteFailure(Throwable t) {
        quoteView.onError(t);
    }
}
