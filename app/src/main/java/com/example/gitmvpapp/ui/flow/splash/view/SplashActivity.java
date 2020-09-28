package com.example.gitmvpapp.ui.flow.splash.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.LifecycleObserver;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.gitmvpapp.R;
import com.example.gitmvpapp.di.component.ApplicationComponent;
import com.example.gitmvpapp.di.component.DaggerSplashActivityComponent;
import com.example.gitmvpapp.di.module.SplashActivityModule;
import com.example.gitmvpapp.ui.flow.base.activity.BaseActivity;
import com.example.gitmvpapp.ui.flow.splash.contract.SplashView;
import com.example.gitmvpapp.ui.flow.splash.presenter.SplashPresenter;

import timber.log.Timber;

public class SplashActivity extends BaseActivity implements SplashView {

    @InjectPresenter
    SplashPresenter presenter;

    public static Intent createClearIntent(Context context) {
        return new Intent(context, SplashActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return presenter;
    }

    @Override
    protected void initDependencies(ApplicationComponent applicationComponent) {
        DaggerSplashActivityComponent.builder().applicationComponent(applicationComponent)
                .splashActivityModule(new SplashActivityModule()).build().inject(presenter);
    }

    @Override
    public void openLoginScreen() {
//        startActivity(LoginActivity.createClearIntent(this));
//        finish();
    }

    @Override
    public void openMainScreen() {
//        startActivity(MainActivity.createIntent(this));
//        finish();
    }

    @Override
    public void updateLoading(boolean show) {
        Timber.i("Update loading");
    }

    @Override
    public void showError(String message) {
        showDialog().info(message);
    }
}