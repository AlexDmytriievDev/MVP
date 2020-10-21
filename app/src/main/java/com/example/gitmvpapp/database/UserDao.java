package com.example.gitmvpapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gitmvpapp.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface UserDao {

    @Insert
    Completable addUser(User user);

    @Query("SELECT * FROM users")
    Maybe<List<User>> getUsers();

    @Query("SELECT * FROM users WHERE isSignIn = 1")
    Maybe<User> getSignInUser();

    @Update
    Completable updateUser(User user);

    @Delete
    Completable deleteUser(User user);

    @Query("DELETE FROM users")
    Completable clear();
}
