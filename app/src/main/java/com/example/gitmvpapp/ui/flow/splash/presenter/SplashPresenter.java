package com.example.gitmvpapp.ui.flow.splash.presenter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.gitmvpapp.model.user.User;
import com.example.gitmvpapp.network.Repository;
import com.example.gitmvpapp.ui.flow.splash.contract.SplashView;
import com.example.gitmvpapp.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> implements LifecycleObserver {

    private static final int SPLASH_SEC_DELAY = 3;

    @Inject
    RxUtils rxUtils;

    @Inject
    Repository repository;

    @Inject
    CompositeDisposable disposables;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        disposables.add(rxUtils.zipWithTimer(repository.getUser(), SPLASH_SEC_DELAY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setupUser, error ->
                        getViewState().showError(error.getMessage())));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposables.clear();
    }

    private void setupUser(User user) {
        if (user != null && user.isExist()) {
            getViewState().openMainScreen();
        } else {
            getViewState().openLoginScreen();
        }
    }
}
