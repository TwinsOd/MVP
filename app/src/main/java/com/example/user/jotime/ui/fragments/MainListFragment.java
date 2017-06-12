package com.example.user.jotime.ui.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.jotime.App;
import com.example.user.jotime.R;
import com.example.user.jotime.data.callback.TimeCallback;
import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.data.model.SettingModel;
import com.example.user.jotime.ui.RunDetailsListener;
import com.example.user.jotime.ui.adapter.TimeAdapter;
import com.example.user.jotime.ui.customView.DateTextView;
import com.example.user.jotime.ui.customView.PersonTextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainListFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private final String FROM_INTERVAL_KEY = "from_interval_key";
    private final String TILL_INTERVAL_KEY = "till_interval_key";
    private PersonTextView idText;
    private SettingModel settingModel;
    private DateTextView fromDateText;
    private DateTextView tillDateText;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context mContext;
    private RunDetailsListener runDetailsListener;
    private int id;

    public MainListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getRepository().getId(new TimeCallback<Integer>() {
            @Override
            public void onEmit(Integer data) {
                id = data;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mContext = getContext();

        settingModel = new SettingModel(id);
        Calendar calendar = Calendar.getInstance();
        settingModel.setTillDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        settingModel.setFromDate(calendar.getTimeInMillis());
        idText = (PersonTextView) view.findViewById(R.id.title_id);
        idText.setId(id);
        idText.setOnClickListener(this);
        fromDateText = (DateTextView) view.findViewById(R.id.from_interval);
        fromDateText.setLongDate(settingModel.getFromDate());
        fromDateText.setOnClickListener(this);
        tillDateText = (DateTextView) view.findViewById(R.id.till_interval);
        tillDateText.setLongDate(settingModel.getTillDate());
        tillDateText.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        loadData();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_id:
                showDialogChangeId();
                break;
            case R.id.from_interval:
                showDatePickerDialog(settingModel.getFromDate(), FROM_INTERVAL_KEY);
                break;
            case R.id.till_interval:
                showDatePickerDialog(settingModel.getTillDate(), TILL_INTERVAL_KEY);
                break;
        }
    }

    public void setDetailsListener(RunDetailsListener listener) {
        runDetailsListener = listener;
    }

    private void showDatePickerDialog(long date, String tag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, this, year, monthOfYear, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    private void showDialogChangeId() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(R.string.choose_new_id);
        final EditText idEditText = new EditText(mContext);
        idEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialog.setView(idEditText);

        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (idEditText.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, R.string.no_id_card, Toast.LENGTH_SHORT).show();
                } else {
                    idText.setId(idEditText.getText().toString());
                    loadData();
                }
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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        if (view.getTag().toString().equals(FROM_INTERVAL_KEY)) {
            settingModel.setFromDate(calendar.getTimeInMillis());
            fromDateText.setLongDate(settingModel.getFromDate());
        } else if (view.getTag().toString().equals(TILL_INTERVAL_KEY)) {
            settingModel.setTillDate(calendar.getTimeInMillis());
            tillDateText.setLongDate(settingModel.getTillDate());
        }
        loadData();
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        App.getRepository().getList(settingModel, new TimeCallback<List<ItemModel>>() {
            @Override
            public void onEmit(final List<ItemModel> data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TimeAdapter timeAdapter = new TimeAdapter(mContext, data, runDetailsListener);
                        recyclerView.setAdapter(timeAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCompleted() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, R.string.error_loading_data, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }
}
