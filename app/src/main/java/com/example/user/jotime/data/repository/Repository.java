package com.example.user.jotime.data.repository;


import android.support.annotation.NonNull;

import com.example.user.jotime.data.callback.TimeCallback;
import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.data.model.SettingModel;

import java.util.List;

public interface Repository {

    void getList(SettingModel model, @NonNull TimeCallback<List<ItemModel>> callback);

    void getId(@NonNull TimeCallback<Integer> callback);

    void setId(int id, @NonNull TimeCallback callback);
}
