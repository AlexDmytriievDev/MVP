package com.example.gitmvpapp.di.module;

import android.content.Context;

import com.example.gitmvpapp.di.qualifier.ApplicationContext;
import com.example.gitmvpapp.di.qualifier.ErrorsInterceptor;
import com.example.gitmvpapp.di.qualifier.HeaderInterceptor;
import com.example.gitmvpapp.di.qualifier.LoggingInterceptor;
import com.example.gitmvpapp.di.qualifier.NetworkInterceptor;
import com.example.gitmvpapp.di.scope.PerApplication;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module(includes = {InterceptorsModule.class})
public class NetworkModule {

    private static final int TIMEOUT = 60; // seconds
    private static final int CACHE_SIZE = 10 * 1024 * 1024; //10MB cache

    @Provides
    @PerApplication
    Cache cache(@ApplicationContext Context context) {
        return new Cache(context.getCacheDir(), CACHE_SIZE);
    }

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(@LoggingInterceptor Interceptor loggingInterceptor,
                                     @NetworkInterceptor Interceptor networkInterceptor,
                                     @HeaderInterceptor Interceptor headerInterceptor,
                                     @ErrorsInterceptor Interceptor errorsInterceptor,
                                     Cache cache) {
        return new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .followRedirects(false)
                .cache(cache)
                .addInterceptor(networkInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(errorsInterceptor)
                .build();
    }
}

