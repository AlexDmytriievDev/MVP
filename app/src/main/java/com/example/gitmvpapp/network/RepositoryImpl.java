package com.example.gitmvpapp.network;

import com.example.gitmvpapp.model.base.BaseResponse;

import io.reactivex.Single;

public class RepositoryImpl implements Repository {

    private final RestApi restApi;

    public RepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<BaseResponse> getBaseResponse() {
        return restApi.getBaseResponse();
    }
}
