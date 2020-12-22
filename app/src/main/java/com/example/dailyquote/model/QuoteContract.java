package com.example.dailyquote.model;

public final class QuoteContract {
    public static final String BASE_URL = "https://quotes.rest/";

    public static final String ROOT = "contents";

    public static final String QUOTES_ARRAY = "quotes";

    public enum CATEGORIES {
        inspire, management, sports, life, funny, love, art, students
    }

    private QuoteContract() {
    }
}
