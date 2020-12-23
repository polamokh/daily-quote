package com.example.dailyquote.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.example.dailyquote.R;

public class QuoteSharedPreference {

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

    public static void saveQuoteExpireTime(Context context, long time) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(
                context.getString(R.string.key_expire_time), time);

        editor.apply();
    }

    public static String getUserCategoryDailyQuote(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(
                context.getString(R.string.key_user_category),
                QuoteContract.CATEGORIES.inspire.toString());
    }

    public static void setUserCategoryDailyQuote(Context context, String category) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(
                context.getString(R.string.key_user_category), category);
        editor.apply();
    }

    public static boolean isJobScheduled(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(
                context.getString(R.string.key_job_schedule),
                false);
    }

    public static void setJobScheduleFlag(Context context, boolean flag) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(
                context.getString(R.string.key_job_schedule), flag);
        editor.apply();
    }
}
