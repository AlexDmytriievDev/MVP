package com.example.gitmvpapp.di.module;

import com.example.gitmvpapp.BuildConfig;
import com.example.gitmvpapp.di.scope.PerApplication;
import com.example.gitmvpapp.network.RestApi;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @PerApplication
    RestApi provideApi(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build();
    }
}
