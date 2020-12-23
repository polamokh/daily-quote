package com.example.dailyquote.utils;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.dailyquote.model.IQuoteInteractor;
import com.example.dailyquote.model.QuoteSharedPreference;
import com.example.dailyquote.sync.QuoteSyncNetwork;
import com.example.dailyquote.sync.QuoteSyncWorker;

import java.util.concurrent.TimeUnit;

public class QuoteSyncUtils {

    private static void scheduleJobSchedule(Context context) {
        QuoteSharedPreference.setJobScheduleFlag(context, true);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(QuoteSyncWorker.class,
                1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.DAYS)
                .build();

        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(QuoteSyncWorker.UNIQUE_WORKER_NAME,
                        ExistingPeriodicWorkPolicy.REPLACE,
                        request);
    }

    public static void initialize(Context context, String language, String category,
                                  IQuoteInteractor.IQuoteListener listener) {

        if (!QuoteSharedPreference.isJobScheduled(context))
            scheduleJobSchedule(context);

        if (QuoteSharedPreference.loadQuoteData(context).getQuote() == null)
            getRemoteQuote(context, language, category, listener);
        else
            getLocalQuote(context, listener);
    }

    private static void getRemoteQuote(Context context, String language, String category,
                                       IQuoteInteractor.IQuoteListener listener) {
        QuoteSyncNetwork.syncQuote(context, language, category, listener);
    }

    private static void getLocalQuote(Context context, IQuoteInteractor.IQuoteListener listener) {
        listener.onQuoteLoaded(QuoteSharedPreference.loadQuoteData(context));
    }
}
