package com.example.user.jotime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.jotime.R;
import com.example.user.jotime.data.SharedPreference.SharedPreferenceManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextId = (EditText)findViewById(R.id.id_edit_text);

        findViewById(R.id.enter_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = editTextId.getText().toString();
                if (!idText.isEmpty()){
                    SharedPreferenceManager.saveId(Integer.valueOf(idText), LoginActivity.this);
                    startMainActivity();
                }else {
                    Toast.makeText(LoginActivity.this, R.string.fill_id, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
