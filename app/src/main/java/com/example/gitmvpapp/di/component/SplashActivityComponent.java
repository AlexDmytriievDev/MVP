package com.example.gitmvpapp.di.component;

import com.example.gitmvpapp.di.module.SplashActivityModule;
import com.example.gitmvpapp.di.scope.PerActivity;
import com.example.gitmvpapp.ui.flow.splash.presenter.SplashPresenter;

import dagger.Component;

@PerActivity
@Component(modules = {SplashActivityModule.class}, dependencies = {ApplicationComponent.class})
public interface SplashActivityComponent {

    void inject(SplashPresenter presenter);
}
