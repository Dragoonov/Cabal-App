package com.cabal.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cabal.app.models.EventModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EventDetailsFragment extends Fragment {

    private ImageView imageViewEvent;
    private TextView textViewNameEvent;
    private TextView textViewDescriptionEvent;
    private TextView textViewOwnerEvent;
    private TextView textViewDateEvent;
    private TextView textViewPlaceEvent;
    private ListView listOfMembers;

    public static EventDetailsFragment newInstance(EventModel eventModel) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putString("image", eventModel.getImage());
        args.putString("name", eventModel.getName());
        args.putString("description", eventModel.getDescription());
        args.putString("creator", eventModel.getCreator());
        args.putString("date", eventModel.getDate());
        args.putString("location", eventModel.getLocation());
        args.putStringArray("members", eventModel.getMembers());

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        imageViewEvent = view.findViewById(R.id.imageEvent);
        textViewNameEvent = view.findViewById(R.id.nameEvent);
        textViewDescriptionEvent = view.findViewById(R.id.descriptionEvent);
        textViewOwnerEvent = view.findViewById(R.id.ownerEvent);
        textViewDateEvent = view.findViewById(R.id.dateEvent);
        textViewPlaceEvent = view.findViewById(R.id.placeEvent);
        listOfMembers = view.findViewById(R.id.listOfMembers);

        setDataInEventDetails();

        return view;
    }

    private void setDataInEventDetails() {

        if(getArguments() != null) {
            Glide.with(getContext()).load(getArguments().getString("image")).into(imageViewEvent);

            textViewNameEvent.setText(getArguments().getString("name"));

            textViewDescriptionEvent.setText(getArguments().getString("description"));

            String ownerEventStr = "Event owner: " + getArguments().getString("creator");
            textViewOwnerEvent.setText(ownerEventStr);

            String dateStr = "Date: " + " " + getArguments().getString("date");
            textViewDateEvent.setText(dateStr);

            String locationEventStr = "Location: " + getArguments().getString("location");
            textViewPlaceEvent.setText(locationEventStr);

            List<String> members = Arrays.asList(Objects.requireNonNull(getArguments().getStringArray("members")));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, members);
            listOfMembers.setAdapter(adapter);
        }
    }
}
