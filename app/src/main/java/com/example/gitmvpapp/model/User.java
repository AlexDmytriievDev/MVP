package com.example.gitmvpapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.gitmvpapp.utils.Constants;

import java.util.Objects;
import java.util.UUID;

@Entity(tableName = Constants.USERS)
public class User implements Parcelable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.ID)
    private String id;

    @ColumnInfo(name = Constants.FIRST_NAME)
    private String firstName;

    @ColumnInfo(name = Constants.LAST_NAME)
    private String lastName;

    @ColumnInfo(name = Constants.AVATAR_URL)
    private String avatarUrl;

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    @Ignore
    public User(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Ignore
    protected User(Parcel in) {
        id = Objects.requireNonNull(in.readString());
        firstName = in.readString();
        lastName = in.readString();
        avatarUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(avatarUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean hasFullName() {
        return !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName);
    }
}
