package uk.ac.ncl.team15.android.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.function.Consumer;
import java.util.function.Function;

public class DialogHelper {
    // adapted from: https://stackoverflow.com/questions/10903754/input-text-dialog-android
    public static void textInputDialog(Context context,
                                       String title, String posText, String negText, String defaultVal,
                                       Consumer<String> okCallback, Function<String, Boolean> validationFunc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Set up the input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        if (defaultVal != null)
            input.setText(defaultVal);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(posText, ((dialog, which) -> okCallback.accept(input.getText().toString())));
        builder.setNegativeButton(negText, ((dialog, which) -> dialog.cancel()));

        AlertDialog alertDialog = builder.create();

        if (validationFunc != null) {
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setEnabled(validationFunc.apply(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        alertDialog.show();
    }

    public static void dropdownSelectionDialog(Context context,
                                               String title, String[] options,
                                               String negText,
                                               Consumer<String> okCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Set up the input
        final ListView lvDropdown = new ListView(context);
        lvDropdown.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1, options));
        builder.setView(lvDropdown);

        // Set up the buttons
        //builder.setPositiveButton(posText, ((dialog, which) -> okCallback.accept((String)lvDropdown.getSelectedItem())));
        builder.setNegativeButton(negText, ((dialog, which) -> dialog.cancel()));

        AlertDialog alertDialog = builder.create();

        lvDropdown.setOnItemClickListener((parent, view, position, id) -> {
            okCallback.accept((String)lvDropdown.getItemAtPosition(position));
            alertDialog.dismiss();
        });

        alertDialog.show();
    }
}
