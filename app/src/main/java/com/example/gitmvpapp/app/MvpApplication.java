package com.example.gitmvpapp.app;

import android.app.Application;

import androidx.annotation.IntDef;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.gitmvpapp.BuildConfig;
import com.example.gitmvpapp.R;
import com.example.gitmvpapp.di.component.ApplicationComponent;
import com.example.gitmvpapp.di.component.DaggerApplicationComponent;
import com.example.gitmvpapp.di.module.ApplicationModule;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import timber.log.Timber;

import static com.example.gitmvpapp.app.MvpApplication.AppState.BACKGROUND;
import static com.example.gitmvpapp.app.MvpApplication.AppState.FOREGROUND;
import static com.example.gitmvpapp.app.MvpApplication.AppState.UNDEFINED;

public class MvpApplication extends Application implements LifecycleObserver {

    @AppState
    private int appState;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onMoveToForeground() {
        Timber.d(getString(R.string.foreground_state));
        appState = FOREGROUND;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onMoveToBackground() {
        Timber.d(getString(R.string.background_state));
        appState = BACKGROUND;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public int getAppState() {
        return appState;
    }

    private void initDependencies() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    @IntDef({UNDEFINED, FOREGROUND, BACKGROUND})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AppState {

        int UNDEFINED = 0;

        int FOREGROUND = 1;

        int BACKGROUND = 2;
    }
}
