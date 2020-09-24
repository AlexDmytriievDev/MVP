package com.example.gitmvpapp.exceptions;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import com.example.gitmvpapp.model.base.BaseResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.gitmvpapp.exceptions.InternalException.Code.CODE_ERROR;
import static com.example.gitmvpapp.exceptions.InternalException.Code.CODE_SUCCESS;


public class InternalException extends RuntimeException implements Parcelable {

    private final BaseResponse response;

    public InternalException(BaseResponse response) {
        this.response = response;
    }

    protected InternalException(Parcel in) {
        response = in.readParcelable(BaseResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(response, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InternalException> CREATOR = new Creator<InternalException>() {
        @Override
        public InternalException createFromParcel(Parcel in) {
            return new InternalException(in);
        }

        @Override
        public InternalException[] newArray(int size) {
            return new InternalException[size];
        }
    };

    @Override
    public String toString() {
        return "InternalException{" +
                "response=" + response +
                '}';
    }

    public BaseResponse getResponse() {
        return response;
    }

    @IntDef({CODE_SUCCESS, CODE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Code {

        int CODE_SUCCESS = 0;

        int CODE_ERROR = 1;
    }
}
