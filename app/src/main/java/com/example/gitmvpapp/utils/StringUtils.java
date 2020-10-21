package com.example.gitmvpapp.utils;

import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class StringUtils {

    public static String getText(TextView textView) {
        return textView != null && textView.getText() != null
                ? textView.getText().toString().trim() : "";
    }

    public static String getText(TextInputLayout inputLayout) {
        return inputLayout != null ? getText(inputLayout.getEditText()) : "";
    }
}
