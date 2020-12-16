package com.example.dailyquote.model;

import android.os.AsyncTask;

import com.example.dailyquote.utils.JsonUtils;

public class Quote implements IQuote {
    private String text;
    private String author;
    private String category;

    private static OnFinishedListener listener;

    public Quote() {
    }

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
        Quote.listener = listener;
        new Task().execute(QuoteContract.BASE_URL);
    }

    public static class Task extends AsyncTask<String, Void, Quote> {

        @Override
        protected Quote doInBackground(String... strings) {
            if (strings != null && strings.length > 0) {
                return JsonUtils.fetchQuoteData(strings[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Quote quote) {
            super.onPostExecute(quote);
            listener.onFinished(quote);
        }
    }
}
