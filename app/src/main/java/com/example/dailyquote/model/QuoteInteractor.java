package com.example.dailyquote.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.example.dailyquote.R;
import com.example.dailyquote.utils.JsonUtils;

import java.util.Date;

public class QuoteInteractor implements IQuoteInteractor {
    private Context context;

    public QuoteInteractor(Context context) {
        this.context = context;
    }

    @Override
    public void getQuote(final OnFinishedListener listener) {
        new AsyncTask<String, Void, Quote>() {
            @Override
            protected Quote doInBackground(String... strings) {
                if (strings != null && strings.length > 0) {
                    Quote quote;
                    if (checkLastTime()) {
                        quote = JsonUtils.fetchQuoteData(strings[0]);
                        saveQuoteData(quote);
                    } else
                        quote = loadQuoteData();

                    return quote;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Quote quote) {
                super.onPostExecute(quote);
                listener.onFinished(quote);
            }
        }.execute(QuoteContract.BASE_URL);
    }

    private boolean checkLastTime() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        long date = Long.parseLong(
                preferences.getString(
                        context.getString(R.string.key_expire_time), "0"
                )
        );

        return date < new Date().getTime();
    }

    private void saveQuoteData(Quote quote) {
        if (quote != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(
                    context.getString(R.string.key_category), quote.getCategory()
            );
            editor.putString(
                    context.getString(R.string.key_text), quote.getText()
            );
            editor.putString(
                    context.getString(R.string.key_author), quote.getAuthor()
            );
            editor.putString(
                    context.getString(R.string.key_background), quote.getBackground()
            );
            editor.putString(
                    context.getString(R.string.key_expire_time), String.valueOf(quote.getExpire())
            );
            editor.apply();
        }
    }

    private Quote loadQuoteData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String category = preferences.getString(
                context.getString(R.string.key_category), null
        );
        String text = preferences.getString(
                context.getString(R.string.key_text), null
        );
        String author = preferences.getString(
                context.getString(R.string.key_author), null
        );
        String background = preferences.getString(
                context.getString(R.string.key_background), null
        );
        long expireTime = Long.parseLong(preferences.getString(
                context.getString(R.string.key_expire_time), "0"
        ));

        return new Quote(text, author, category, background, expireTime);
    }
}
