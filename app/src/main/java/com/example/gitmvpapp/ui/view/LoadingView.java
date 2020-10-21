package com.example.gitmvpapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gitmvpapp.R;

import butterknife.ButterKnife;

public class LoadingView extends FrameLayout {

    public LoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs,
                       int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.view_loading, this, true);
        }
        ButterKnife.bind(this);
    }
}
