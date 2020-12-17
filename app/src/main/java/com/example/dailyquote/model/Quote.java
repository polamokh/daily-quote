package com.example.dailyquote.model;

import android.os.AsyncTask;

import com.example.dailyquote.utils.JsonUtils;

public class Quote {
    private String text;
    private String author;
    private String category;
    private String background;
    private long expire;


    public Quote(String text, String author, String category, String background, long expire) {
        this.text = text;
        this.author = author;
        this.category = category;
        this.background = background;
        this.expire = expire;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
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

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
