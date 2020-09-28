package com.example.gitmvpapp.di.module;

import android.content.Context;
import android.text.TextUtils;

import com.example.gitmvpapp.BuildConfig;
import com.example.gitmvpapp.di.qualifier.ApplicationContext;
import com.example.gitmvpapp.di.qualifier.ErrorsInterceptor;
import com.example.gitmvpapp.di.qualifier.HeaderInterceptor;
import com.example.gitmvpapp.di.qualifier.LoggingInterceptor;
import com.example.gitmvpapp.di.qualifier.NetworkInterceptor;
import com.example.gitmvpapp.di.scope.PerApplication;
import com.example.gitmvpapp.exceptions.InternalException;
import com.example.gitmvpapp.exceptions.NoInternetException;
import com.example.gitmvpapp.exceptions.ResponseException;
import com.example.gitmvpapp.model.BaseResponse;
import com.example.gitmvpapp.utils.NetworkUtils;
import com.google.gson.Gson;

import java.util.Objects;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;


@Module
public class InterceptorsModule {

    @Provides
    @PerApplication
    @NetworkInterceptor
    Interceptor provideConnectivityInterceptor(@ApplicationContext Context appContext) {
        return chain -> {
            if (!NetworkUtils.isNetworkAvailable(appContext)) {
                throw new NoInternetException();
            }

            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        };
    }

    @Provides
    @PerApplication
    @LoggingInterceptor
    Interceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);
    }

    @Provides
    @PerApplication
    @HeaderInterceptor
    Interceptor provideHeaderInterceptor() {
        return chain -> chain.proceed(chain.request().newBuilder().build());
    }

    @Provides
    @PerApplication
    @ErrorsInterceptor
    Interceptor provideErrorsInterceptor() {
        return chain -> {
            Request request = chain.request();
            Response response = chain.proceed(request);
            ResponseBody responseBody = response.body();

            MediaType contentType = Objects.requireNonNull(responseBody).contentType();
            String bodyString = responseBody.string();
            responseBody.close();

            if (!TextUtils.isEmpty(bodyString)) {
                BaseResponse model;
                try {
                    model = new Gson().fromJson(bodyString, BaseResponse.class);
                } catch (IllegalStateException e) {
                    Timber.e(e);
                    throw new ResponseException("Invalid server response");
                }

                if (model.withError()) {
                    Timber.e("Error url: %s", request.url());
                    Timber.e("Error response: %s", model.toString());
                    throw new InternalException(model);
                }
            } else {
                throw new ResponseException("Invalid server response");
            }

            ResponseBody body = ResponseBody.create(contentType, bodyString);
            return response.newBuilder().body(body).build();
        };
    }
}

