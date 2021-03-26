package me.polamokh.dailyquote.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import me.polamokh.dailyquote.R;
import me.polamokh.dailyquote.network.QuoteClient;
import me.polamokh.dailyquote.utils.SharedPreferenceUtils;

public class ChooseCategoryDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String category = SharedPreferenceUtils.getUserCategoryDailyQuote(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.category_dialog_message)
                .setSingleChoiceItems(getCategories(),
                        QuoteClient.CATEGORIES.valueOf(category).ordinal(), this);

        return builder.create();
    }

    private String[] getCategories() {
        QuoteClient.CATEGORIES[] categories = QuoteClient.CATEGORIES.values();
        String[] strings = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            strings[i] = categories[i].toString();
        }

        return strings;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String category = QuoteClient.CATEGORIES.values()[which].toString();

        SharedPreferenceUtils.setUserCategoryDailyQuote(getContext(), category);

        Toast.makeText(getContext(),
                String.format("Daily category changed to %s. Tomorrow it will be updated.", category),
                Toast.LENGTH_LONG)
                .show();

        dialog.dismiss();
    }
}
