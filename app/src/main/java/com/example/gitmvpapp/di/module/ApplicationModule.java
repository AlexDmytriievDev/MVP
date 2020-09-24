package com.example.gitmvpapp.di.module;

import android.app.Application;
import android.content.Context;

import com.example.gitmvpapp.app.MvpApplication;
import com.example.gitmvpapp.di.qualifier.ApplicationContext;
import com.example.gitmvpapp.di.scope.PerApplication;
import com.example.gitmvpapp.network.Repository;
import com.example.gitmvpapp.network.RepositoryImpl;
import com.example.gitmvpapp.network.RestApi;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application app;

    public ApplicationModule(MvpApplication app) {
        this.app = app;
    }

    @Provides
    @PerApplication
    @ApplicationContext
    Context providesAppContext() {
        return app;
    }

    @Provides
    @PerApplication
    Repository provideRepository(RestApi restApi) {
        return new RepositoryImpl(restApi);
    }
}
