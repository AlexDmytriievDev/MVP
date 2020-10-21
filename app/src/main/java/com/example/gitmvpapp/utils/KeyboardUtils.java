package com.example.gitmvpapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

public final class KeyboardUtils implements ViewTreeObserver.OnGlobalLayoutListener {

    private boolean mIsKeyboardShown;
    private int mInitialHeightsDiff = -1;
    private View rootView;
    private OnKeyboardShowListener listener;

    @Inject
    KeyboardUtils() {
    }

    public static void hideSoftKeyboard(Activity activity) {
        hideSoftKeyboard(activity.getCurrentFocus());
    }

    public static void hideSoftKeyboard(@Nullable View... views) {
        if (views == null) return;
        try {
            InputMethodManager manager = (InputMethodManager) views[0].getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            for (View currentView : views) {
                if (null == currentView) continue;
                if (manager != null) {
                    manager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
                }
                currentView.clearFocus();
            }
        } catch (NullPointerException ignored) {
        }
    }

    public static void showSoftKeyboard(@Nullable View view) {
        if (view == null) return;
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        if (manager != null) {
            manager.showSoftInput(view, 0);
        }
    }

    public static void showSoftKeyboard(@NonNull Context context) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public static void hideSoftKeyboard(@NonNull Context context) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public void addKeyboardShowListener(@NonNull final View rootView,
                                        @NonNull final OnKeyboardShowListener listener) {
        if (listener == this.listener) {
            removeKeyboardListener();
        }

        this.rootView = rootView;
        this.listener = listener;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void addKeyboardShowListener(@NonNull final Activity activity,
                                        @NonNull final OnKeyboardShowListener listener) {
        addKeyboardShowListener(activity.getWindow().getDecorView().getRootView(), listener);
    }

    public void removeKeyboardListener() {
        listener = null;
        if (rootView != null) {
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            rootView = null;
        }
    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);

        int heightDiff = rootView.getRootView().getHeight() - (r.bottom - r.top);
        if (mInitialHeightsDiff == -1) {
            mInitialHeightsDiff = heightDiff;
        }
        heightDiff -= mInitialHeightsDiff;

        if (heightDiff > 80) { // if more than 100 pixels, its probably a keyboard...
            if (!mIsKeyboardShown) {
                mIsKeyboardShown = true;
                listener.onKeyboardShow(true);
            }
        } else if (heightDiff < 50) {
            if (mIsKeyboardShown) {
                mIsKeyboardShown = false;
                listener.onKeyboardShow(false);
            }
        }
    }

    public interface OnKeyboardShowListener {
        void onKeyboardShow(boolean show);
    }
}
