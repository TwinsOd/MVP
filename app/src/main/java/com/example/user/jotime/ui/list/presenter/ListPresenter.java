package com.example.user.jotime.ui.list.presenter;


import android.support.annotation.NonNull;

import com.example.user.jotime.data.model.SettingModel;
import com.example.user.jotime.ui.base.Presenter;
import com.example.user.jotime.ui.list.view.ListView;

import java.util.List;

public interface ListPresenter extends Presenter<ListView> {
    void getBaseData();

    void loadData(@NonNull SettingModel model);

    void onDateClick(List<String> data);

    void saveBaseData(@NonNull SettingModel model);
}
