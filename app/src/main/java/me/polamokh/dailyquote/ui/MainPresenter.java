package me.polamokh.dailyquote.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.polamokh.dailyquote.MainContract;
import me.polamokh.dailyquote.domain.Quote;
import me.polamokh.dailyquote.network.QuoteClient;
import me.polamokh.dailyquote.network.ResponseDTO;
import me.polamokh.dailyquote.utils.SharedPreferenceUtils;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {
    private final Context context;
    private ExecutorService executor;
    private Handler handler;
    private MainContract.View quoteView;

    public MainPresenter(Context context, MainContract.View quoteView) {
        this.context = context;
        this.quoteView = quoteView;
    }

    @Override
    public void onDestroy() {
        executor.shutdown();
        quoteView = null;
    }

    @Override
    public void onGetQuote(String category) {
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            if (SharedPreferenceUtils.loadQuoteData(context).getQuote() == null) {
                try {
                    syncQuoteFromNetwork(category);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Quote quote = SharedPreferenceUtils.loadQuoteData(context);
            handler.post(() -> quoteView.setQuote(quote));
        });
    }

    private void syncQuoteFromNetwork(String category) throws IOException {
        Response<ResponseDTO> response = QuoteClient.getInstance()
                .getDailyQuote("en", category)
                .execute();

        if (response.isSuccessful()) {
            Quote quote = response.body().asDomainModel();
            SharedPreferenceUtils.saveQuoteData(context, quote);
        }
    }
}
