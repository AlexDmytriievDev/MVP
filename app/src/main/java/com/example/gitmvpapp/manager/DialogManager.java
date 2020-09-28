package com.example.gitmvpapp.manager;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.example.gitmvpapp.R;
import com.example.gitmvpapp.ui.flow.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogManager {

    private final Activity activity;
    private final List<AppCompatDialog> dialogs = new ArrayList<>();

    public DialogManager(Activity activity) {
        this.activity = activity;
    }

    public void info(String description) {
        showDialog(false, null, description, null, null, null);
    }

    public void info(String description,  DialogClickListener listener) {
        showDialog(false, null, description, null, null, listener);
    }

    public void info(String title, String description, DialogClickListener listener) {
        showDialog(false, title, description, null, null, listener);
    }

    public void info(String title,
                     String description,
                     String positiveText,
                      DialogClickListener listener) {
        showDialog(false, title, description, positiveText, null, listener);
    }

    public void confirm(String description,  DialogClickListener listener) {
        showDialog(true, null, description, null, null, listener);
    }

    public void confirm(String title, String description,  DialogClickListener listener) {
        showDialog(true, title, description, null, null, listener);
    }

    public void confirm(String title,
                        String description,
                        String positiveText,
                        String negativeText,
                        DialogClickListener listener) {
        showDialog(true, title, description, positiveText, negativeText, listener);
    }

    private void showDialog(boolean isConfirmation,
                            String title,
                            String description,
                            String positiveText,
                            String negativeText,
                             DialogClickListener listener) {
        View dialogView = activity.getLayoutInflater().inflate(R.layout.view_alert_dialog, null);
        AppCompatDialog dialog = new AlertDialog.Builder(activity).setView(dialogView).create();
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView dialogTitle = dialogView.findViewById(R.id.alertDialogTitle);
        TextView dialogDescription = dialogView.findViewById(R.id.alertDialogDescription);
        TextView dialogConfirm = dialogView.findViewById(R.id.alertDialogConfirm);
        TextView dialogCancel = dialogView.findViewById(R.id.alertDialogCancel);

        if (!TextUtils.isEmpty(positiveText)) dialogConfirm.setText(positiveText);
        if (!TextUtils.isEmpty(negativeText)) dialogCancel.setText(negativeText);

        if (!TextUtils.isEmpty(title)) {
            dialogTitle.setText(title);
            dialogTitle.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(description)) {
            dialogDescription.setText(description);
            dialogDescription.setVisibility(View.VISIBLE);
        }

        dialogConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onButtonClick(true);
            }
            dialogs.remove(dialog);
            dialog.dismiss();
        });

        if (isConfirmation) {
            dialogCancel.setVisibility(View.VISIBLE);
            dialogCancel.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onButtonClick(false);
                }
                dialogs.remove(dialog);
                dialog.dismiss();
            });
        }

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialogs.add(dialog);
        dialog.show();
    }

    public void closeDialogs() {
        for (AppCompatDialog dialog : dialogs)
            if (dialog.isShowing()) dialog.dismiss();
    }

    public interface DialogClickListener {
        void onButtonClick(boolean isPositive);
    }
}
