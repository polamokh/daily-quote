package me.polamokh.dailyquote.network;

import me.polamokh.dailyquote.domain.Quote;

public class ResponseDTO {
    private ContentDTO contents;

    public ContentDTO getContents() {
        return contents;
    }

    public void setContents(ContentDTO contents) {
        this.contents = contents;
    }

    public Quote asDomainModel() {
        return new Quote(contents.getQuotes().get(0).getQuote(),
                contents.getQuotes().get(0).getAuthor(),
                contents.getQuotes().get(0).getCategory(),
                contents.getQuotes().get(0).getBackground());
    }
}
