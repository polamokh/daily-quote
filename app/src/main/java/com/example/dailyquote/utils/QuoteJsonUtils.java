package com.example.dailyquote.utils;

import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteContract;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class QuoteJsonUtils {


    public static Quote extractFeatureFromJson(String json) {
        if (json == null)
            return null;

        try {
            JSONObject base = new JSONObject(json).getJSONObject(QuoteContract.ROOT);
            JSONArray quotes = base.getJSONArray(QuoteContract.QUOTES_ARRAY);

            if (quotes.length() > 0) {
                JSONObject quotesJSONObject = quotes.getJSONObject(0);

                return new Gson().fromJson(quotesJSONObject.toString(), Quote.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
