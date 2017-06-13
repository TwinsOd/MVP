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
    public void getId() {
        repository.getId(new TimeCallback<Integer>() {
            @Override
            public void onEmit(Integer data) {
                if (data != null) {
                    if (getView() != null)
                        getView().showId(data);
                }
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
        repository.getList(model, new TimeCallback<List<ItemModel>>() {
            @Override
            public void onEmit(final List<ItemModel> data) {
                getView().showData(data);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TimeAdapter timeAdapter = new TimeAdapter(mContext, data, runDetailsListener);
//                        recyclerView.setAdapter(timeAdapter);
//                    }
//                });
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                getView().showError(throwable.getMessage());
            }
        });
    }

    @Override
    public void onDateClick(List<String> data) {
        if (listener != null)
            listener.clickToRun(data);
    }

}
