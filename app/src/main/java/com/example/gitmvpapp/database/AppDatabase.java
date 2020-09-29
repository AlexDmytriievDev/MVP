package com.example.gitmvpapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gitmvpapp.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
}
