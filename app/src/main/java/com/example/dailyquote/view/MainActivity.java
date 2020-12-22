package com.example.dailyquote.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.dailyquote.R;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.model.QuoteContract;
import com.example.dailyquote.model.QuoteInteractor;
import com.example.dailyquote.model.QuoteSharedPreference;
import com.example.dailyquote.presenter.IQuotePresenter;
import com.example.dailyquote.presenter.QuotePresenter;
import com.example.dailyquote.utils.QuoteShareUtils;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements IQuoteView {
    private TextView quoteText;
    private TextView quoteAuthor;
    private TextView quoteCategory;
    private ImageView background;
    private Toolbar toolbar;

    private Quote quote;

    private IQuotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteText = findViewById(R.id.quote_text);
        quoteAuthor = findViewById(R.id.quote_author);
        quoteCategory = findViewById(R.id.quote_category);
        background = findViewById(R.id.background);
        toolbar = findViewById(R.id.toolbar);

        presenter = new QuotePresenter(this, new QuoteInteractor(this));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.onGetQuote(getDailyQuoteCategory());
    }

    private String getDailyQuoteCategory() {
        String category = QuoteSharedPreference.getUserCategoryDailyQuote(this);
        if (category == null)
            category = QuoteContract.CATEGORIES.inspire.toString();

        return category;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setQuote(Quote quote) {
        if (quote != null) {
            this.quote = quote;
            invalidateOptionsMenu();

            Picasso.get()
                    .load(quote.getBackground())
                    .into(background);
            quoteCategory.setText(quote.getCategory());
            quoteText.setText(String.format("\"%s\"", quote.getQuote()));
            quoteAuthor.setText(String.format("–%s–", quote.getAuthor()));
        }
    }

    @Override
    public void onError(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (quote != null)
            menu.findItem(R.id.action_share).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                DialogFragment dialogFragment = new ChooseCategoryDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), null);
                return true;
            case R.id.action_share:
                QuoteShareUtils.shareQuote(this, quote);
                return true;
            default:
                return false;
        }
    }
}
