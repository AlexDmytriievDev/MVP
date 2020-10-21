package com.example.gitmvpapp.di.component;

import com.example.gitmvpapp.di.module.LoginActivityModule;
import com.example.gitmvpapp.di.module.SplashActivityModule;
import com.example.gitmvpapp.di.scope.PerActivity;
import com.example.gitmvpapp.ui.flow.authorization.login.presenter.LoginPresenter;

import dagger.Component;

@PerActivity
@Component(modules = {LoginActivityModule.class}, dependencies = {ApplicationComponent.class})
public interface LoginActivityComponent {

    void inject(LoginPresenter presenter);
}
