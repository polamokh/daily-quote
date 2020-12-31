package com.example.dailyquote.utils;

import android.content.Context;

import com.evernote.android.job.JobRequest;
import com.example.dailyquote.model.IQuoteInteractor;
import com.example.dailyquote.model.QuoteSharedPreference;
import com.example.dailyquote.sync.QuoteSyncJob;
import com.example.dailyquote.sync.QuoteSyncNetwork;

import java.util.concurrent.TimeUnit;

public class QuoteSyncUtils {

    public static void scheduleSyncJob(Context context) {
        QuoteSharedPreference.setJobScheduleFlag(context, true);

        JobRequest.Builder request = new JobRequest.Builder(QuoteSyncJob.TAG)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setPeriodic(TimeUnit.DAYS.toMillis(1), TimeUnit.HOURS.toMillis(6))
                .setUpdateCurrent(true)
                .setRequirementsEnforced(true);

        request.build().schedule();
    }

    public static void initialize(Context context, String language, String category,
                                  IQuoteInteractor.IQuoteListener listener) {

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
