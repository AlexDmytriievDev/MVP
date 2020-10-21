package com.example.gitmvpapp.manager;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.example.gitmvpapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogManager {

    private static final List<AppCompatDialog> dialogs = new ArrayList<>();

    public static class Builder {

        private final View dialogView;
        private final AppCompatDialog dialog;

        private String title;
        private String description;
        private String positiveText;
        private String negativeText;
        private boolean isCancelAvailable;
        private DialogClickListener listener;

        public Builder(@NonNull Activity activity) {
            dialogView = activity.getLayoutInflater()
                    .inflate(R.layout.view_alert_dialog, null);
            dialog = new AlertDialog.Builder(activity).setView(dialogView).create();
            Objects.requireNonNull(dialog.getWindow())
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPositiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public Builder setCancelAvailable(boolean cancelAvailable) {
            isCancelAvailable = cancelAvailable;
            return this;
        }

        public Builder setListener(DialogClickListener listener) {
            this.listener = listener;
            return this;
        }

        public void show() {
            closeDialogs();
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

            if (isCancelAvailable) {
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
    }

    public static void closeDialogs() {
        for (AppCompatDialog dialog : dialogs) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialogs.clear();
    }

    public interface DialogClickListener {
        void onButtonClick(boolean isPositive);
    }
}
