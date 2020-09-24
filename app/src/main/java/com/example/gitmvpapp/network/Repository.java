package com.example.gitmvpapp.network;

import com.example.gitmvpapp.model.base.BaseResponse;

import io.reactivex.Single;

public interface Repository {

    Single<BaseResponse> getBaseResponse();
}
