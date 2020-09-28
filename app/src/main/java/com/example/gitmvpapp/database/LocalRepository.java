package com.example.gitmvpapp.database;

import com.example.gitmvpapp.model.User;

import java.util.List;

import io.reactivex.Single;

public interface LocalRepository {

    Single<User> addUser(User user);

    Single<User> getUser();

    Single<List<User>> getUsers();

    Single<User> updateUser(User user);

    Single<Boolean> deleteUser(User user);

    Single<Boolean> clear();
}
