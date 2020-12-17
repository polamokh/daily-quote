package com.example.dailyquote.utils;

public class JsonResponse {
    private String body;
    private long expireDate;

    public JsonResponse(String body, long expireDate) {
        this.body = body;
        this.expireDate = expireDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }
}
