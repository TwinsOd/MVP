package com.example.user.jotime.data.repository;


import android.support.annotation.NonNull;

import com.example.user.jotime.data.callback.TimeCallback;
import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.data.model.SettingModel;
import com.example.user.jotime.data.tasks.TimeRunnable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RepositoryImpl implements Repository{
    @Override
    public void getList(SettingModel model, @NonNull TimeCallback<List<ItemModel>> callback) {

        Executor executorTime = Executors.newSingleThreadExecutor();
        executorTime.execute(new TimeRunnable(model, callback));
    }
}
