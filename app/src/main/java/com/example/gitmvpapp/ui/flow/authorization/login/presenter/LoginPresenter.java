package com.example.gitmvpapp.ui.flow.authorization.login.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.gitmvpapp.R;
import com.example.gitmvpapp.database.LocalRepository;
import com.example.gitmvpapp.di.qualifier.ApplicationContext;
import com.example.gitmvpapp.model.User;
import com.example.gitmvpapp.ui.flow.authorization.login.contract.LoginView;
import com.example.gitmvpapp.utils.Constants;
import com.example.gitmvpapp.utils.RxUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> implements LifecycleObserver {

    @Inject
    @ApplicationContext
    Context context;

    @Inject
    RxUtils rxUtils;

    @Inject
    LocalRepository localRepository;

    @Inject
    CompositeDisposable disposables;

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposables.clear();
    }

    public void initLayouts(EditText email, EditText password) {
        disposables.add(RxTextView.textChanges(email)
                .debounce(Constants.DELAY.EDIT_TEXT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> getViewState().updateEmailLayout("")));

        disposables.add(RxTextView.textChanges(password)
                .debounce(Constants.DELAY.EDIT_TEXT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> getViewState().updatePasswordLayout("")));
    }

    public void login(String email, String password) {
        if (validateCredentials(email, password)) {
            getViewState().updateLoading(true);
            disposables.add(rxUtils.zipWithTimer(
                    localRepository.getAll(), Constants.DELAY.LOGIN)
                    .flatMap(users -> validateUser(users, email, password))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> getViewState().updateLoading(false))
                    .subscribe(this::setupResponse, Timber::e));
        }
    }

    private boolean validateCredentials(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            getViewState().updateEmailLayout(context.getString(R.string.error_empty));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getViewState().updateEmailLayout(context.getString(R.string.error_email));
        } else if (TextUtils.isEmpty(password)) {
            getViewState().updatePasswordLayout(context.getString(R.string.error_empty));
        } else if (password.length() < 6) {
            getViewState().updatePasswordLayout(context.getString(R.string.error_password));
        } else {
            return true;
        }
        return false;
    }

    private Single<Boolean> validateUser(List<User> users, String email, String password) {
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                if (user != null && TextUtils.equals(user.getEmail(), email)
                        && TextUtils.equals(user.getPassword(), password)) {
                    user.setSignIn(true);
                    return localRepository.updateUser(user);
                }
            }
        }
        return Single.just(false);
    }

    private void setupResponse(boolean isUserSignIn) {
        if (isUserSignIn) {
            getViewState().openMainScreen();
        } else {
            getViewState().showError(context.getString(R.string.error_user),
                    context.getString(R.string.please_sign_up));
        }
    }
}