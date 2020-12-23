package com.example.dailyquote.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dailyquote.model.IQuoteInteractor;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteSharedPreference;
import com.example.dailyquote.utils.QuoteNotificationUtils;

public class QuoteSyncWorker extends Worker {

    public static final String UNIQUE_WORKER_NAME = "quote-sync";

    public QuoteSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        QuoteSyncNetwork.syncQuote(getApplicationContext(),
                "en",
                QuoteSharedPreference.getUserCategoryDailyQuote(getApplicationContext()),
                new IQuoteInteractor.IQuoteListener() {
                    @Override
                    public void onQuoteLoaded(Quote quote) {
                        QuoteNotificationUtils.notifyUser(getApplicationContext(), quote);
                    }

                    @Override
                    public void onQuoteFailure(Throwable t) {

                    }
                });

        return Result.success();
    }
}
