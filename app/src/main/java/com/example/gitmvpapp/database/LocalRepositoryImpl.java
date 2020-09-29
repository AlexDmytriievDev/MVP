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
    public Single<Boolean> addUser(User newUser) {
        return database.getUserDao().addUser(newUser)
                .toSingle(() -> true).onErrorReturnItem(false);
    }

    @Override
    public Single<User> getSignInUser() {
        return database.getUserDao().getSignInUser()
                .defaultIfEmpty(new User()).toSingle();
    }

    @Override
    public Single<List<User>> getAll() {
        return database.getUserDao().getUsers()
                .defaultIfEmpty(new ArrayList<>()).toSingle();
    }

    @Override
    public Single<Boolean> updateUser(User update) {
        return database.getUserDao().updateUser(update)
                .toSingle(() -> true).onErrorReturnItem(false);
    }

    @Override
    public Single<Boolean> logoutUser() {
        return getSignInUser().flatMap(user -> {
            if (user != null && user.isSignIn()) {
                user.setSignIn(false);
                return updateUser(user);
            } else {
                return Single.just(false);
            }
        }).onErrorReturnItem(false);
    }

    @Override
    public Single<Boolean> deleteUser(User user) {
        return database.getUserDao().deleteUser(user)
                .toSingle(() -> true).onErrorReturnItem(false);
    }

    @Override
    public Single<Boolean> clear() {
        return database.getUserDao().clear()
                .toSingle(() -> true).onErrorReturnItem(false);
    }
}
