package com.example.user.jotime.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.user.jotime.R;
import com.example.user.jotime.ui.RunDetailsListener;
import com.example.user.jotime.ui.fragments.DetailsFragment;
import com.example.user.jotime.ui.fragments.MainListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RunDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        MainListFragment fragment = new MainListFragment();
        fragment.setDetailsListener(this);
        mFragmentTransaction.replace(R.id.container_main, fragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.title_main_activity));
    }

    @Override
    public void clickToRun(ArrayList<String> logList) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container_main, DetailsFragment.getInstance(logList));
        mFragmentTransaction.addToBackStack("DetailsFragment");
        mFragmentTransaction.commit();
    }
}
