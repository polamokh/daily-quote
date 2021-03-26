package me.polamokh.dailyquote;

import me.polamokh.dailyquote.domain.Quote;
import me.polamokh.dailyquote.ui.BasePresenter;
import me.polamokh.dailyquote.ui.BaseView;

public class MainContract {
    public interface Presenter extends BasePresenter {
        void onGetQuote(String category);
    }

    public interface View extends BaseView<Presenter> {
        void setQuote(Quote quote);
    }
}
