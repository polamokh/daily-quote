package me.polamokh.dailyquote.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import me.polamokh.dailyquote.MainContract;
import me.polamokh.dailyquote.R;
import me.polamokh.dailyquote.domain.Quote;
import me.polamokh.dailyquote.utils.ShareUtils;
import me.polamokh.dailyquote.utils.SharedPreferenceUtils;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private TextView quoteText;
    private TextView quoteAuthor;
    private TextView quoteCategory;
    private ImageView background;

    private Quote quote;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteText = findViewById(R.id.quote_text);
        quoteAuthor = findViewById(R.id.quote_author);
        quoteCategory = findViewById(R.id.quote_category);
        background = findViewById(R.id.background);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setPresenter(new MainPresenter(this, this));
        presenter.onGetQuote(SharedPreferenceUtils
                .getUserCategoryDailyQuote(getApplicationContext()));
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setQuote(Quote quote) {
        if (quote.getQuote() != null) {
            findViewById(R.id.error_view).setVisibility(View.GONE);
            this.quote = quote;
            Picasso.get()
                    .load(quote.getBackground())
                    .into(background);
            quoteCategory.setText(quote.getCategory());
            quoteText.setText(String.format("\"%s\"", quote.getQuote()));
            quoteAuthor.setText(String.format("–%s–", quote.getAuthor()));
        } else {
            findViewById(R.id.error_view).setVisibility(View.VISIBLE);
            this.quote = null;
        }
        invalidateOptionsMenu();
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
                ShareUtils.shareQuote(this, quote);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
