package com.example.user.jotime.ui;


import com.example.user.jotime.data.model.ItemListModel;

import java.util.List;

public interface LoadData {
    void success(List<ItemListModel> list);

    void error();
}
