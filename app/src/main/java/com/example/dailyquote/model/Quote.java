package com.example.dailyquote.model;

public class Quote {
    private String quote;
    private String author;
    private String category;
    private String background;

    public Quote(String quote, String author, String category, String background) {
        this.quote = quote;
        this.author = author;
        this.category = category;
        this.background = background;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
