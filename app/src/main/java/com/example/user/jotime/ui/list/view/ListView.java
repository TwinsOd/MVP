package com.example.user.jotime.ui.list.view;


import android.support.annotation.NonNull;

import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.ui.base.ProgressView;
import com.example.user.jotime.ui.base.View;
import com.example.user.jotime.ui.list.presenter.ListPresenter;

import java.util.List;

public interface ListView extends View<ListPresenter>, ProgressView {

    void showData(@NonNull List<ItemModel> list);

    void showId(@NonNull int id);
}
