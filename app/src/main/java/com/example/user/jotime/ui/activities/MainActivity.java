package com.example.user.jotime.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.jotime.App;
import com.example.user.jotime.R;
import com.example.user.jotime.ui.details.view.DetailsFragment;
import com.example.user.jotime.ui.list.presenter.ListPresenter;
import com.example.user.jotime.ui.list.presenter.ListPresenterIml;
import com.example.user.jotime.ui.list.view.MainListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RunDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startListFragment();
    }

    private void startListFragment() {
        ListPresenter listPresenter = new ListPresenterIml(this, App.getRepository());
        MainListFragment fragment = new MainListFragment();
        fragment.bindPresenter(listPresenter);
        listPresenter.bindView(fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_main, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.title_main_activity));
    }

    @Override
    public void clickToRun(List<String> logList) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, DetailsFragment.getInstance(logList))
                .addToBackStack(null)
                .commit();
    }
}
