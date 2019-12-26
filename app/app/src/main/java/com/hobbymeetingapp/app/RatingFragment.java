package com.hobbymeetingapp.app;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hobbymeetingapp.app.Utils.JsonLoader;
import com.hobbymeetingapp.app.navigation_bar.MyEventsFragment;

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
        view.findViewById(R.id.rateOk).setOnClickListener(view1 -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MyEventsFragment()).commit());
        users.setLayoutManager(new LinearLayoutManager(getContext()));
        RatingAdapter adapter = new RatingAdapter(JsonLoader.loadUsers(getContext()));
        users.setAdapter(adapter);

        return view;
    }
}
