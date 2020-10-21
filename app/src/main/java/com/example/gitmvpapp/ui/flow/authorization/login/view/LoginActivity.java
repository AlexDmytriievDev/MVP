package com.example.gitmvpapp.ui.flow.authorization.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LifecycleObserver;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.gitmvpapp.R;
import com.example.gitmvpapp.di.component.ApplicationComponent;
import com.example.gitmvpapp.di.component.DaggerLoginActivityComponent;
import com.example.gitmvpapp.di.module.LoginActivityModule;
import com.example.gitmvpapp.ui.flow.authorization.login.contract.LoginView;
import com.example.gitmvpapp.ui.flow.authorization.login.presenter.LoginPresenter;
import com.example.gitmvpapp.ui.flow.base.activity.BaseActivity;
import com.example.gitmvpapp.ui.view.LoadingView;
import com.example.gitmvpapp.utils.KeyboardUtils;
import com.example.gitmvpapp.utils.StringUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.login_email_input_layout)
    TextInputLayout emailLayout;

    @BindView(R.id.login_password_input_layout)
    TextInputLayout passwordLayout;

    @BindView(R.id.login_loading_view)
    LoadingView loadingView;

    @InjectPresenter
    LoginPresenter presenter;

    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter.initLayouts(Objects.requireNonNull(emailLayout.getEditText()),
                Objects.requireNonNull(passwordLayout.getEditText()));
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return presenter;
    }

    @Override
    protected void initDependencies(ApplicationComponent applicationComponent) {
        DaggerLoginActivityComponent.builder().applicationComponent(applicationComponent)
                .loginActivityModule(new LoginActivityModule()).build().inject(presenter);
    }

    @Override
    public void openSignUpScreen() {
        updateFocus();
        //  TODO
        //  startActivity(SignUpActivity.createIntent(this));
    }

    @Override
    public void openMainScreen() {
        updateFocus();
        //  TODO
        //  startActivity(MainActivity.createIntent(this));
    }

    @Override
    public void updateEmailLayout(String message) {
        emailLayout.setError(message);
    }

    @Override
    public void updatePasswordLayout(String message) {
        passwordLayout.setError(message);
    }

    @Override
    public void updateLoading(boolean show) {
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String title, String description) {
        updateFocus();
        dialog().setTitle(title).setDescription(description).show();
    }

    @OnClick(R.id.login_confirm_btn)
    void onLoginClick() {
        presenter.login(StringUtils.getText(emailLayout), StringUtils.getText(passwordLayout));
    }

    @OnClick(R.id.login_sign_up_btn)
    void onSignUpClick() {
        openSignUpScreen();
    }

    private void updateFocus() {
        KeyboardUtils.hideSoftKeyboard(this);
        Objects.requireNonNull(emailLayout.getEditText()).clearFocus();
        Objects.requireNonNull(passwordLayout.getEditText()).clearFocus();
    }
}
