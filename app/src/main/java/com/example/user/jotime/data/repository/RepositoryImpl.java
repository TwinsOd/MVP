package com.example.user.jotime.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.example.user.jotime.data.callback.TimeCallback;
import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.data.model.SettingModel;
import com.example.user.jotime.data.tasks.TimeRunnable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.user.jotime.AppConstans.APP_PREFERENCES;
import static com.example.user.jotime.AppConstans.DEFAULT_INTERVAL;
import static com.example.user.jotime.AppConstans.KEY_ID_PREFERENCES;
import static com.example.user.jotime.AppConstans.KEY_INTERVALS_DAY_PREFERENCES;

public class RepositoryImpl implements Repository {

    private final SharedPreferences sharedPreferences;
    private final Handler mainUiHandler;


    public RepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mainUiHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void getList(SettingModel model, @NonNull TimeCallback<List<ItemModel>> callback) {
        Executor executorTime = Executors.newSingleThreadExecutor();
        executorTime.execute(new TimeRunnable(model, callback, mainUiHandler));
    }

    @Override
    public void getModel(@NonNull TimeCallback<SettingModel> callback) {
        int id = sharedPreferences.getInt(KEY_ID_PREFERENCES, 0);
        int interval = sharedPreferences.getInt(KEY_INTERVALS_DAY_PREFERENCES, DEFAULT_INTERVAL);
        SettingModel model = new SettingModel();
        model.setId(id);
        model.setIntervalDays(interval);
        callback.onEmit(model);
        callback.onCompleted();
    }

    @Override
    public void saveModel(SettingModel model, @NonNull TimeCallback callback) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID_PREFERENCES, model.getId());
        editor.putInt(KEY_INTERVALS_DAY_PREFERENCES, model.getIntervalDays());
        editor.apply();
        callback.onCompleted();
    }
}
