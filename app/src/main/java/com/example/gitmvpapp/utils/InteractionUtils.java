package com.example.gitmvpapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.example.gitmvpapp.utils.Constants.INTERACTION.HTTPS_URI;
import static com.example.gitmvpapp.utils.Constants.INTERACTION.HTTP_URI;
import static com.example.gitmvpapp.utils.Constants.INTERACTION.PACKAGE;

public class InteractionUtils {

    public static String getSafeUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (URLUtil.isValidUrl(url)) {
                return url;
            } else if (!url.startsWith(HTTP_URI) || !url.startsWith(HTTPS_URI)) {
                return HTTP_URI + url;
            }
        }
        return "";
    }

    public static void openUrl(Context context, @Nullable String url) {
        String safeUrl = getSafeUrl(url);
        if (TextUtils.isEmpty(safeUrl)) {
            Toast.makeText(context, "invalid URL", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }

    public static Intent openSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts(PACKAGE, context.getPackageName(), null));
        return intent;
    }
}

