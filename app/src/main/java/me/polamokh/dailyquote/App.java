package me.polamokh.dailyquote;

import android.app.Application;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import me.polamokh.dailyquote.work.QuoteSyncWork;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        setupRecurringWork();
    }

    private void setupRecurringWork() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build();

            PeriodicWorkRequest repeatingRequest = new PeriodicWorkRequest.Builder(
                    QuoteSyncWork.class, 1, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .setInitialDelay(1, TimeUnit.DAYS)
                    .build();

            WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(
                    QuoteSyncWork.TAG,
                    ExistingPeriodicWorkPolicy.KEEP,
                    repeatingRequest);
        });
    }
}
