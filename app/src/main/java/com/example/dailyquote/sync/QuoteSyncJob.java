package com.example.dailyquote.sync;

import androidx.annotation.NonNull;

import com.evernote.android.job.Job;
import com.example.dailyquote.model.QuoteSharedPreference;

public class QuoteSyncJob extends Job {

    public static final String TAG = "quote-sync";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        boolean isCompleted =
                QuoteSyncNetwork.syncQuote(getContext(),
                        "en",
                        QuoteSharedPreference.getUserCategoryDailyQuote(getContext()));

        if (isCompleted)
            return Result.SUCCESS;

        return Result.FAILURE;
    }
}
