package com.cabal.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cabal.app.Utils.JsonLoader;

import java.util.Objects;


public class RatingFragment extends Fragment {

    public RatingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        RecyclerView users = view.findViewById(R.id.recycleUsers);
        view.findViewById(R.id.rateOk).setOnClickListener(view1 -> {
            FragmentManager manager = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager();
            manager.beginTransaction()
                .remove(this)
                .commit();
            manager.popBackStack();
                });
        users.setLayoutManager(new LinearLayoutManager(getContext()));
        RatingAdapter adapter = new RatingAdapter(JsonLoader.INSTANCE.loadUsers(getContext()));
        users.setAdapter(adapter);

        return view;
    }
}
