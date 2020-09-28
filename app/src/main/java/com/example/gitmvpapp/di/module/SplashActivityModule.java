package com.example.gitmvpapp.di.module;

import com.example.gitmvpapp.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class SplashActivityModule {

    @Provides
    @PerActivity
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
