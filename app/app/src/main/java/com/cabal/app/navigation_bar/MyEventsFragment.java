package com.cabal.app.navigation_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cabal.app.AddEventFragment;
import com.cabal.app.MyEventsAdapter;
import com.cabal.app.R;
import com.cabal.app.Utils.Filters;
import com.cabal.app.Utils.JsonLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MyEventsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);
        FloatingActionButton btnAddEvent = view.findViewById(R.id.btnAddEvent);
        CheckBox authorFilter = view.findViewById(R.id.authorSwitch);
        CheckBox guestFilter = view.findViewById(R.id.guestSwitch);
        CheckBox finishedFilter = view.findViewById(R.id.finishedSwitch);
        MyEventsAdapter adapter = new MyEventsAdapter(JsonLoader.INSTANCE.loadEvents(view.getContext()));
        authorFilter.setOnCheckedChangeListener((v, checked) -> {
            Filters.INSTANCE.setAUTHOR_FILTER(checked);
            adapter.getFilter().filter("");
        });
        guestFilter.setOnCheckedChangeListener((v, checked) -> {
            Filters.INSTANCE.setGUEST_FILTER(checked);
            adapter.getFilter().filter("");
        });
        finishedFilter.setOnCheckedChangeListener((v, checked) -> {
            Filters.INSTANCE.setFINISHED_FILTER(checked);
            adapter.getFilter().filter("");
        });
        btnAddEvent.setOnClickListener(view1 -> Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AddEventFragment())
                .addToBackStack(null)
                .commit());
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
