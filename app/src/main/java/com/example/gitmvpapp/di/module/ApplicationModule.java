package com.example.gitmvpapp.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.gitmvpapp.BuildConfig;
import com.example.gitmvpapp.app.MvpApplication;
import com.example.gitmvpapp.database.AppDatabase;
import com.example.gitmvpapp.database.LocalRepository;
import com.example.gitmvpapp.database.LocalRepositoryImpl;
import com.example.gitmvpapp.di.qualifier.ApplicationContext;
import com.example.gitmvpapp.di.scope.PerApplication;
import com.example.gitmvpapp.network.RestApi;
import com.example.gitmvpapp.network.ServerRepository;
import com.example.gitmvpapp.network.ServerRepositoryImpl;

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
    LocalRepository provideLocalRepository(@ApplicationContext Context context) {
        return new LocalRepositoryImpl(Room.databaseBuilder(
                context, AppDatabase.class, BuildConfig.DB_NAME).build());
    }

    @Provides
    @PerApplication
    ServerRepository provideServerRepository(RestApi restApi) {
        return new ServerRepositoryImpl(restApi);
    }
}
