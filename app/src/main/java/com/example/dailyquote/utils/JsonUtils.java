package com.example.dailyquote.utils;

import android.text.TextUtils;

import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.dailyquote.utils.NetworkUtils.createHttpRequest;
import static com.example.dailyquote.utils.NetworkUtils.createUrl;

public final class JsonUtils {


    public static Quote fetchQuoteData(String requestUrl) {
        JsonResponse jsonResponse = null;
        try {
            jsonResponse = createHttpRequest(createUrl(requestUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractFeatureFromJson(jsonResponse);
    }


    private static Quote extractFeatureFromJson(JsonResponse json) {
        if (json == null)
            return null;

        try {
            JSONObject base = new JSONObject(json.getBody()).getJSONObject(QuoteContract.ROOT);
            JSONArray quotes = base.getJSONArray(QuoteContract.QUOTES_ARRAY);

            if (quotes.length() > 0) {
                JSONObject quote = quotes.getJSONObject(0);

                return new Quote(quote.getString(QuoteContract.QuoteEntry.TEXT),
                        quote.getString(QuoteContract.QuoteEntry.AUTHOR),
                        quote.getString(QuoteContract.QuoteEntry.CATEGORY),
                        quote.getString(QuoteContract.QuoteEntry.BACKGROUND),
                        json.getExpireDate());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
