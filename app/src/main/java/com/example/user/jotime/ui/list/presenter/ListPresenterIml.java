package com.example.user.jotime.ui.list.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.user.jotime.data.callback.TimeCallback;
import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.data.model.SettingModel;
import com.example.user.jotime.data.repository.Repository;
import com.example.user.jotime.ui.activities.RunDetailsListener;
import com.example.user.jotime.ui.base.BasePresenter;
import com.example.user.jotime.ui.list.view.ListView;

import java.util.List;

public class ListPresenterIml extends BasePresenter<ListView> implements ListPresenter {
    private Repository repository;
    private RunDetailsListener listener;

    public ListPresenterIml(@Nullable RunDetailsListener listener, @NonNull Repository repository) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public void getBaseData() {
        repository.getModel(new TimeCallback<SettingModel>() {
            @Override
            public void onEmit(SettingModel data) {
                if (data != null && getView() != null)
                    getView().showBaseData(data);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    @Override
    public void loadData(@NonNull SettingModel model) {
        showProgress();
        repository.getList(model, new TimeCallback<List<ItemModel>>() {
            @Override
            public void onEmit(final List<ItemModel> data) {
                if (data != null && getView() != null)
                    getView().showList(data);
            }

            @Override
            public void onCompleted() {
                hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                if (getView() != null)
                    getView().showError(throwable.getMessage());
            }
        });
    }

    @Override
    public void onDateClick(List<String> data) {
        if (listener != null)
            listener.clickToRun(data);
    }

    @Override
    public void saveBaseData(@NonNull SettingModel model) {
        repository.saveModel(model, new TimeCallback() {
            @Override
            public void onEmit(Object data) {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                if (getView() != null)
                    getView().showError(throwable.getMessage());
            }
        });
    }

    private void showProgress() {
        if (getView() != null)
            getView().showProgress();
    }

    private void hideProgress() {
        if (getView() != null)
            getView().hideProgress();
    }
}
