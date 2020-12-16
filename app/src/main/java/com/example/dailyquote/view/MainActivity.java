package com.example.dailyquote.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.dailyquote.R;
import com.example.dailyquote.model.Quote;
import com.example.dailyquote.presenter.IQuotePresenter;
import com.example.dailyquote.presenter.QuotePresenter;

public class MainActivity extends AppCompatActivity implements IQuoteView {
    private TextView quoteText;
    private TextView quoteAuthor;
    private TextView quoteCategory;

    private IQuotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteText = findViewById(R.id.quote_text);
        quoteAuthor = findViewById(R.id.quote_author);
        quoteCategory = findViewById(R.id.quote_category);

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
        quoteCategory.setText(quote.getCategory());
        quoteText.setText(String.format("\"%s\"", quote.getText()));
        quoteAuthor.setText(String.format("–%s–", quote.getAuthor()));
    }
}
