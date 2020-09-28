package com.example.gitmvpapp.network;

import com.example.gitmvpapp.model.user.User;

import io.reactivex.Single;

public class RepositoryImpl implements Repository {

    private final RestApi restApi;

    public RepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<User> getUser() {
        return Single.just(new User());
    }
}
