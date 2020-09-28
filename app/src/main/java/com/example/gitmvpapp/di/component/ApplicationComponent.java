package com.example.gitmvpapp.di.component;

import android.content.Context;

import com.example.gitmvpapp.database.LocalRepository;
import com.example.gitmvpapp.di.module.ApiModule;
import com.example.gitmvpapp.di.module.ApplicationModule;
import com.example.gitmvpapp.di.qualifier.ApplicationContext;
import com.example.gitmvpapp.di.scope.PerApplication;
import com.example.gitmvpapp.network.ServerRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@PerApplication
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context provideAppContext();

    LocalRepository provideLocalRepository();

    ServerRepository provideServerRepository();
}

