package com.example.user.jotime.ui.list.presenter;


import android.support.annotation.NonNull;

import com.example.user.jotime.data.model.SettingModel;
import com.example.user.jotime.ui.base.Presenter;
import com.example.user.jotime.ui.list.view.ListView;

import java.util.List;

public interface ListPresenter extends Presenter<ListView> {
    void getId();

    void loadData(@NonNull SettingModel model);

    void onDateClick(List<String> data);
}
