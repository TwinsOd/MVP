package com.example.user.jotime;


import android.app.Application;

import com.example.user.jotime.data.repository.Repository;
import com.example.user.jotime.data.repository.RepositoryImpl;

public class App extends Application{

    private static Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new RepositoryImpl(this);
    }

    public static Repository getRepository(){
        return repository;
    }
}
