package com.example.user.jotime.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.jotime.App;
import com.example.user.jotime.R;
import com.example.user.jotime.data.SharedPreference.SharedPreferenceManager;
import com.example.user.jotime.data.callback.TimeCallback;
import com.example.user.jotime.data.utils.NetworkUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!NetworkUtils.checkWifiOnAndConnected(getApplicationContext()))
            Toast.makeText(this, R.string.no_connection_to_wi_fi, Toast.LENGTH_LONG).show();

        App.getRepository().getId(new TimeCallback<Integer>() {
            @Override
            public void onEmit(Integer data) {
                if (data.equals(0)){
                    startLoginActivity();
                }else {
                    startMainActivity();
                }
            }

            @Override
            public void onCompleted() {
                startLoginActivity();
            }

            @Override
            public void onError(Throwable throwable) {
                startLoginActivity();
            }
        });

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
