package com.example.gitmvpapp.ui.flow.splash.presenter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.gitmvpapp.database.LocalRepository;
import com.example.gitmvpapp.model.User;
import com.example.gitmvpapp.ui.flow.splash.contract.SplashView;
import com.example.gitmvpapp.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> implements LifecycleObserver {

    private static final int SPLASH_SEC_DELAY = 3;

    @Inject
    RxUtils rxUtils;

    @Inject
    LocalRepository localRepository;

    @Inject
    CompositeDisposable disposables;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        disposables.add(rxUtils.zipWithTimer(localRepository.getSignInUser(), SPLASH_SEC_DELAY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setupResponse, Timber::e));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposables.clear();
    }

    private void setupResponse(User user) {
        if (user != null && user.isSignIn()) {
            getViewState().openMainScreen();
        } else {
            getViewState().openLoginScreen();
        }
    }
}
