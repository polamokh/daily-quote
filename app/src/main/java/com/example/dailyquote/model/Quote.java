package com.example.dailyquote.model;

import com.example.dailyquote.utils.JsonUtils;

public class Quote implements IQuote {
    private String text;
    private String author;
    private String category;

    public Quote(String text, String author, String category) {
        this.text = text;
        this.author = author;
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void getQuote(OnFinishedListener listener) {
        listener.onFinished(JsonUtils.fetchQuoteData(QuoteContract.BASE_URL));
    }
}
