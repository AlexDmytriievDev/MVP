package com.example.gitmvpapp.database;

import com.example.gitmvpapp.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class LocalRepositoryImpl implements LocalRepository {

    private final AppDatabase database;

    public LocalRepositoryImpl(AppDatabase database) {
        this.database = database;
    }

    @Override
    public Single<User> addUser(User newUser) {
        return Single.just(newUser).flatMap(user -> {
            database.userDao().addUser(user);
            return Single.just(user);
        });
    }

    @Override
    public Single<User> getUser() {
        return database.userDao().getUser().onErrorReturnItem(new User());
    }

    @Override
    public Single<List<User>> getUsers() {
        return database.userDao().getUsers().onErrorReturnItem(new ArrayList<>());
    }

    @Override
    public Single<User> updateUser(User update) {
        return Single.just(update).flatMap(user -> {
            database.userDao().updateUser(user);
            return Single.just(user);
        });
    }

    @Override
    public Single<Boolean> deleteUser(User user) {
        return Single.just(true).flatMap(success -> {
            database.userDao().deleteUser(user);
            return Single.just(success);
        });
    }

    @Override
    public Single<Boolean> clear() {
        return Single.just(true).flatMap(success -> {
            database.userDao().clear();
            return Single.just(success);
        });
    }
}
