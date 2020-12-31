package com.example.dailyquote;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.example.dailyquote.sync.JobCreator;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        JobManager.create(this).addJobCreator(new JobCreator());
    }
}
