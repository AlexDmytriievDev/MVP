package com.example.gitmvpapp.di.component;

import android.content.Context;

import com.example.gitmvpapp.di.module.ApiModule;
import com.example.gitmvpapp.di.module.ApplicationModule;
import com.example.gitmvpapp.di.qualifier.ApplicationContext;
import com.example.gitmvpapp.di.scope.PerApplication;
import com.example.gitmvpapp.network.Repository;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context provideAppContext();

    Repository provideRepository();
}

