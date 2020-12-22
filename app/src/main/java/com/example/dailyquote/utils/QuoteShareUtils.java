package com.example.dailyquote.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.ShareCompat;

import com.example.dailyquote.model.Quote;

public class QuoteShareUtils {
    public static void shareQuote(Activity activity, Quote quote) {
        Intent intent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(String.format("\"%s\"\n\n–%s–", quote.getQuote(), quote.getAuthor()))
                .getIntent();

        activity.startActivity(Intent.createChooser(intent, null));
    }
}
