package com.example.gitmvpapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.example.gitmvpapp.network.Constants.CHAR.AMPERSAND;
import static com.example.gitmvpapp.network.Constants.CHAR.EQUALLY;
import static com.example.gitmvpapp.network.Constants.CONFIG.WEB_VIEW_ENCODING;


public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Observable<Boolean> isNetworkAvailableObservable(Context context) {
        return Observable.just(NetworkUtils.isNetworkAvailable(context));
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String query = url.getQuery();
        String[] pairs = query.split(AMPERSAND);
        for (String pair : pairs) {
            int idx = pair.indexOf(EQUALLY);
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), WEB_VIEW_ENCODING),
                    URLDecoder.decode(pair.substring(idx + 1), WEB_VIEW_ENCODING));
        }
        return query_pairs;
    }
}
