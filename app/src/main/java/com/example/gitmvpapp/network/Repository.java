package com.example.gitmvpapp.network;

import com.example.gitmvpapp.model.user.User;

import io.reactivex.Single;

public interface Repository {

    Single<User> getUser();
}
