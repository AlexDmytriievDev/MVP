package com.example.gitmvpapp.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class RxUtils {

    @Inject
    RxUtils() {
    }

    public <T> Single<T> zipWithTimer(@NonNull Single<T> single, int secondsDelay) {
        return Single.zip(Single.timer(secondsDelay, TimeUnit.SECONDS), single,
                (timerValue, originalValue) -> originalValue);
    }

    public <T> Observable<T> zipWithSecondDelay(@NonNull Observable<T> observable) {
        return Observable.zip(Observable.timer(1, TimeUnit.SECONDS), observable,
                (timerValue, originalValue) -> originalValue);
    }
}
