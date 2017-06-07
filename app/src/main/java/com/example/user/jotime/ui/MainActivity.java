package com.example.user.jotime.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.jotime.R;
import com.example.user.jotime.adapter.TimeAdapter;
import com.example.user.jotime.data.SharedPreference.SharedPreferenceManager;
import com.example.user.jotime.data.model.ItemListModel;
import com.example.user.jotime.data.model.UserModel;
import com.example.user.jotime.net.TimeRunnable;
import com.example.user.jotime.ui.customView.DateTextView;
import com.example.user.jotime.ui.customView.PersonTextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, LoadData {

    private final String FROM_INTERVAL_KEY = "from_interval_key";
    private final String TILL_INTERVAL_KEY = "till_interval_key";
    private PersonTextView idText;
    private UserModel userModel;
    private DateTextView fromDateText;
    private DateTextView tillDateText;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.title_main_activity));

        int id = SharedPreferenceManager.getId(this);
        userModel = new UserModel(id);
        Calendar calendar = Calendar.getInstance();
        userModel.setTillDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        userModel.setFromDate(calendar.getTimeInMillis());
        idText = (PersonTextView) findViewById(R.id.title_id);
        idText.setId(id);
        idText.setOnClickListener(this);
        fromDateText = (DateTextView) findViewById(R.id.from_interval);
        fromDateText.setLongDate(userModel.getFromDate());
        fromDateText.setOnClickListener(this);
        tillDateText = (DateTextView) findViewById(R.id.till_interval);
        tillDateText.setLongDate(userModel.getTillDate());
        tillDateText.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_id:
                showDialogChangeId();
                break;
            case R.id.from_interval:
                showDatePickerDialog(userModel.getFromDate(), FROM_INTERVAL_KEY);
                break;
            case R.id.till_interval:
                showDatePickerDialog(userModel.getTillDate(), TILL_INTERVAL_KEY);
                break;
        }
    }

    private void showDatePickerDialog(long date, String tag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, MainActivity.this, year, monthOfYear, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    private void showDialogChangeId() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.choose_new_id);
        final EditText idEditText = new EditText(this);
        idEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialog.setView(idEditText);

        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idText.setId(idEditText.getText().toString());
                loadData();
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        Executor executorTime = Executors.newSingleThreadExecutor();
        executorTime.execute(new TimeRunnable(userModel, this));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        if (view.getTag().toString().equals(FROM_INTERVAL_KEY)) {
            userModel.setFromDate(calendar.getTimeInMillis());
            fromDateText.setLongDate(userModel.getFromDate());
        } else if (view.getTag().toString().equals(TILL_INTERVAL_KEY)){
            userModel.setTillDate(calendar.getTimeInMillis());
            tillDateText.setLongDate(userModel.getTillDate());
        }
        loadData();
    }

    @Override
    public void success(final List<ItemListModel> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimeAdapter timeAdapter = new TimeAdapter(MainActivity.this, list);
                recyclerView.setAdapter(timeAdapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void error() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, R.string.error_loading_data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
