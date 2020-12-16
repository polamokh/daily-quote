package com.example.dailyquote.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dailyquote.R;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.presenter.IQuotePresenter;
import com.example.dailyquote.presenter.QuotePresenter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements IQuoteView {
    private TextView quoteText;
    private TextView quoteAuthor;
    private TextView quoteCategory;
    private ImageView toolbar;

    private IQuotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteText = findViewById(R.id.quote_text);
        quoteAuthor = findViewById(R.id.quote_author);
        quoteCategory = findViewById(R.id.quote_category);
        toolbar = findViewById(R.id.toolbar);

        presenter = new QuotePresenter(this, new Quote());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setQuote(Quote quote) {
        if (quote != null) {
            Picasso.get()
                    .load(quote.getBackground())
                    .into(toolbar);
            quoteCategory.setText(quote.getCategory());
            quoteText.setText(String.format("\"%s\"", quote.getText()));
            quoteAuthor.setText(String.format("–%s–", quote.getAuthor()));
        }
    }
}
