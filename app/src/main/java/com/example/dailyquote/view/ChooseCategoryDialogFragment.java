package com.example.dailyquote.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.dailyquote.R;
import com.example.dailyquote.model.QuoteContract;
import com.example.dailyquote.model.QuoteSharedPreference;

public class ChooseCategoryDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String category = QuoteSharedPreference.getUserCategoryDailyQuote(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.category_dialog_message)
                .setSingleChoiceItems(getCategories(),
                        QuoteContract.CATEGORIES.valueOf(category).ordinal(), this);

        return builder.create();
    }

    private String[] getCategories() {
        QuoteContract.CATEGORIES[] categories = QuoteContract.CATEGORIES.values();
        String[] strings = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            strings[i] = categories[i].toString();
        }

        return strings;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String category = QuoteContract.CATEGORIES.values()[which].toString();

        QuoteSharedPreference.setUserCategoryDailyQuote(getContext(), category);

        Toast.makeText(getContext(),
                String.format("Daily category changed to %s. Tomorrow it will be updated.", category),
                Toast.LENGTH_LONG)
                .show();

        dialog.dismiss();
    }
}
