package com.example.dailyquote.utils;

import android.text.TextUtils;

import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class JsonUtils {
    private static final int HTTP_READ_TIMEOUT = 1000;
    private static final int HTTP_CONNECT_TIMEOUT = 1500;

    private static URL createUrl(String strUrl) {
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Quote fetchQuoteData(String requestUrl) {
        String jsonResponse = null;
        try {
            jsonResponse = createHttpRequest(createUrl(requestUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractFeatureFromJson(jsonResponse);
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }

    private static String createHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(HTTP_READ_TIMEOUT);
            urlConnection.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static Quote extractFeatureFromJson(String json) {
        if (TextUtils.isEmpty(json))
            return null;

        try {
            JSONObject base = new JSONObject(json).getJSONObject(QuoteContract.ROOT);
            JSONArray quotes = base.getJSONArray(QuoteContract.QUOTES_ARRAY);

            if (quotes.length() > 0) {
                JSONObject quote = quotes.getJSONObject(0);

                return new Quote(quote.getString(QuoteContract.QuoteEntry.TEXT),
                        quote.getString(QuoteContract.QuoteEntry.AUTHOR),
                        quote.getString(QuoteContract.QuoteEntry.CATEGORY));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
