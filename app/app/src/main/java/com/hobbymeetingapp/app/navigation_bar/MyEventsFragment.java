package com.hobbymeetingapp.app.navigation_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hobbymeetingapp.app.AddEventFragment;
import com.hobbymeetingapp.app.MyEventsAdapter;
import com.hobbymeetingapp.app.R;
import com.hobbymeetingapp.app.Utils.Filters;
import com.hobbymeetingapp.app.Utils.JsonLoader;

public class MyEventsFragment extends Fragment {

    private Switch mineFilter;
    private Switch finishedFilter;
    private Button btnAddEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);
        btnAddEvent = view.findViewById(R.id.btnAddEvent);
        mineFilter = view.findViewById(R.id.mineSwitch);
        finishedFilter = view.findViewById(R.id.finishedSwitch);
        MyEventsAdapter adapter = new MyEventsAdapter(JsonLoader.loadEvents(view.getContext()));
        mineFilter.setOnCheckedChangeListener((v, checked) -> {
            Filters.MINE_FILTER = checked;
            adapter.getFilter().filter("");
        });
        finishedFilter.setOnCheckedChangeListener((v,checked) -> {
            Filters.FINISHED_FILTER = checked;
            adapter.getFilter().filter("");
        });

        btnAddEvent.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddEventFragment()).commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycleMyEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }
}
