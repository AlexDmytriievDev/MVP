package com.example.gitmvpapp.ui.flow.authorization.login.contract;

import com.example.gitmvpapp.ui.flow.base.contract.BaseView;

public interface LoginView extends BaseView {

    void openSignUpScreen();

    void openMainScreen();

    void updateEmailLayout(String message);

    void updatePasswordLayout(String message);
}
