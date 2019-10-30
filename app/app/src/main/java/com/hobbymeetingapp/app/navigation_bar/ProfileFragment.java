package com.hobbymeetingapp.app.navigation_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hobbymeetingapp.app.EditProfileFragment;
import com.hobbymeetingapp.app.R;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    Button btnEditProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listOfInterests = view.findViewById(R.id.listOfInterests);

        btnEditProfile = view.findViewById(R.id.btnEditProfile);

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


        btnEditProfile.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new EditProfileFragment()).commit();
        });

        return view;
    }

}
