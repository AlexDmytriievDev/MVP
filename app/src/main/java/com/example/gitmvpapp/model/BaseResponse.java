package com.example.gitmvpapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gitmvpapp.exceptions.InternalException;
import com.example.gitmvpapp.utils.Constants;
import com.google.gson.annotations.SerializedName;

public class BaseResponse implements Parcelable {

    @InternalException.Code
    @SerializedName(Constants.CODE)
    private int code;

    @SerializedName(Constants.MESSAGE)
    private String message;

    public BaseResponse() {
    }

    protected BaseResponse(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel in) {
            return new BaseResponse(in);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    @InternalException.Code
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean withError() {
        return code != 0;
    }
}
