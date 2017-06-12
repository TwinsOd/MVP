package com.example.user.jotime.data.callback;


public interface TimeCallback<T> {
    void onEmit(T data);

    void onCompleted();

    void onError(Throwable throwable);
}