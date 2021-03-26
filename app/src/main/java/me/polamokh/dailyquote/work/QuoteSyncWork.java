package me.polamokh.dailyquote.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;

import me.polamokh.dailyquote.network.QuoteClient;
import me.polamokh.dailyquote.network.ResponseDTO;
import me.polamokh.dailyquote.utils.NotificationUtils;
import me.polamokh.dailyquote.utils.SharedPreferenceUtils;
import retrofit2.Response;

public class QuoteSyncWork extends Worker {

    public static final String TAG = "QuoteSync";

    public QuoteSyncWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<ResponseDTO> response = QuoteClient.getInstance()
                    .getDailyQuote("en",
                            SharedPreferenceUtils.getUserCategoryDailyQuote(getApplicationContext()))
                    .execute();
            if (response.isSuccessful()) {
                SharedPreferenceUtils.saveQuoteData(getApplicationContext(), response.body().asDomainModel());
                NotificationUtils.notifyUser(getApplicationContext(),
                        SharedPreferenceUtils.loadQuoteData(getApplicationContext()));
                return Result.success();
            }
            return Result.failure();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
    }
}
