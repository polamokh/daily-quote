package me.polamokh.dailyquote.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import me.polamokh.dailyquote.R;
import me.polamokh.dailyquote.domain.Quote;
import me.polamokh.dailyquote.network.QuoteClient;

public class SharedPreferenceUtils {

    public static void saveQuoteData(Context context, @NonNull Quote quote) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(
                context.getString(R.string.key_category), quote.getCategory());

        editor.putString(
                context.getString(R.string.key_quote), quote.getQuote());

        editor.putString(
                context.getString(R.string.key_author), quote.getAuthor());

        editor.putString(
                context.getString(R.string.key_background), quote.getBackground());

        editor.apply();
    }

    public static Quote loadQuoteData(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String category = preferences.getString(
                context.getString(R.string.key_category), null);

        String text = preferences.getString(
                context.getString(R.string.key_quote), null);

        String author = preferences.getString(
                context.getString(R.string.key_author), null);

        String background = preferences.getString(
                context.getString(R.string.key_background), null);

        return new Quote(text, author, category, background);
    }

    public static String getUserCategoryDailyQuote(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(
                context.getString(R.string.key_user_category),
                QuoteClient.CATEGORIES.inspire.toString());
    }

    public static void setUserCategoryDailyQuote(Context context, String category) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(
                context.getString(R.string.key_user_category), category);
        editor.apply();
    }
}
