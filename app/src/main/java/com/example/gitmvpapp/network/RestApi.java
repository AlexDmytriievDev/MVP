package com.example.gitmvpapp.network;

import com.example.gitmvpapp.model.base.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestApi {

    @GET(Constants.API.TEST)
    Single<BaseResponse> getBaseResponse();
}
