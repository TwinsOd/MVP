package com.example.user.jotime.ui.base;


public interface View<P extends Presenter> {

    void bindPresenter(P presenter);

    P getPresenter();
}
