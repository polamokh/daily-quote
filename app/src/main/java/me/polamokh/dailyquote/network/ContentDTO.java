package me.polamokh.dailyquote.network;

import java.util.List;

public class ContentDTO {
    private List<QuoteDTO> quotes;

    public List<QuoteDTO> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteDTO> quotes) {
        this.quotes = quotes;
    }
}
