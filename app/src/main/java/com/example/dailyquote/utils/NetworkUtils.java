package com.example.dailyquote.utils;

import android.util.Log;

import com.example.dailyquote.model.QuoteContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class NetworkUtils {
    private static final int HTTP_READ_TIMEOUT = 1000;
    private static final int HTTP_CONNECT_TIMEOUT = 1500;

    public static URL createUrl(String strUrl) {
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
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

    public static JsonResponse createHttpRequest(URL url) throws IOException {
        JsonResponse jsonResponse = null;
        if (url == null)
            return null;

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
                jsonResponse = new JsonResponse(readFromStream(inputStream),
                        urlConnection.getHeaderFieldDate(QuoteContract.QuoteEntry.EXPIRE, 0));
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
}
