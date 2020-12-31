package com.example.dailyquote.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evernote.android.job.Job;

public class JobCreator implements com.evernote.android.job.JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String s) {
        switch (s) {
            case QuoteSyncJob.TAG:
                return new QuoteSyncJob();
            default:
                return null;
        }
    }
}
