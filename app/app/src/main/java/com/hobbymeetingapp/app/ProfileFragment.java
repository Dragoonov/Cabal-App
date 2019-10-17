package com.hobbymeetingapp.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listOfInterests = view.findViewById(R.id.listOfInterests);

        ArrayList<String> interests = new ArrayList<String>();
        interests.add("Siatkowka");
        interests.add("Pilka nozna");
        interests.add("Szachy");
        interests.add("Warcaby");
        interests.add("Pilka nozna");
        interests.add("Szachy");
        interests.add("Warcaby");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, interests);
        listOfInterests.setAdapter(adapter);

        return view;
    }

}
