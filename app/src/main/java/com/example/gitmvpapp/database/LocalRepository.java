package com.example.gitmvpapp.database;

import com.example.gitmvpapp.model.User;

import java.util.List;

import io.reactivex.Single;

public interface LocalRepository {

    Single<Boolean> addUser(User user);

    Single<User> getSignInUser();

    Single<List<User>> getAll();

    Single<Boolean> updateUser(User user);

    Single<Boolean> logoutUser();

    Single<Boolean> deleteUser(User user);

    Single<Boolean> clear();
}
