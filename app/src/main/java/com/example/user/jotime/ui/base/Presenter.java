package com.example.user.jotime.ui.base;


public interface Presenter<V extends View> {

    void bindView(V view);

    void unbindView();

    V getView();
}
