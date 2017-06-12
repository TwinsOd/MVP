package com.example.user.jotime.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.jotime.R;
import com.example.user.jotime.ui.adapter.LogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    private static final String PARAM_LIST = "param_list";
    private List<String> listLog;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment getInstance(ArrayList<String> list) {
        DetailsFragment postFragment = new DetailsFragment();
        Bundle args = new Bundle(1);
        args.putStringArrayList(PARAM_LIST, list);
        postFragment.setArguments(args);
        return postFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listLog = getArguments().getStringArrayList(PARAM_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_log);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new LogAdapter(listLog));

        String[] array = listLog.get(0).split(" ");

        TextView nameTextView = (TextView) view.findViewById(R.id.name_text);
        nameTextView.setText(array[2] + " " + array[3]);
        TextView dateTextView = (TextView) view.findViewById(R.id.date_text);
        dateTextView.setText(array[0]);

        return view;
    }

}
