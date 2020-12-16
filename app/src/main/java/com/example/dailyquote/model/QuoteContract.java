package com.example.dailyquote.model;

import java.net.URL;

public final class QuoteContract {
    public static final String BASE_URL = "https://quotes.rest/qod?language=en";

    public static final String ROOT = "contents";

    public static final String QUOTES_ARRAY = "quotes";

    private QuoteContract() {
    }

    public static final class QuoteEntry {
        public static final String TEXT = "quote";

        public static final String AUTHOR = "author";

        public static final String CATEGORY = "category";

        public static final String BACKGROUND = "background";
    }
}
