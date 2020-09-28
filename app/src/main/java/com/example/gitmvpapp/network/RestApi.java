package com.example.gitmvpapp.network;

import com.example.gitmvpapp.model.BaseResponse;
import com.example.gitmvpapp.utils.Constants;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestApi {

    @GET(Constants.API.TEST)
    Single<BaseResponse> getBaseResponse();
}
