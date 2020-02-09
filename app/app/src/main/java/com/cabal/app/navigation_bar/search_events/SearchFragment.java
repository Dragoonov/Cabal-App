package com.cabal.app.navigation_bar.search_events;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cabal.app.R;
import com.cabal.app.utils.JsonLoader;
import com.cabal.app.utils.SwipeType;
import com.cabal.app.dialogs.FilterEventsDialogFragment;
import com.cabal.app.models.EventModel;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class SearchFragment extends Fragment implements EventCard.SwipeListener, FilterEventsDialogFragment.AlertDialogListener {

    private Map<String, Integer> swipeCounter;
    private SwipePlaceHolderView mSwipeView;
    private List<EventModel> events;
    private List<EventModel> filteredEvents;
    private List<EventModel> acceptedEvents;
    private List<EventModel> rejectedEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeView = view.findViewById(R.id.swipeView);
        Context mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeType(SwipeType.HORIZONTAL)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_accept)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_reject));

        filteredEvents.forEach(event -> mSwipeView.addView(new EventCard(mContext, event, mSwipeView, this)));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            events = Objects.requireNonNull(JsonLoader.INSTANCE.loadEvents(getContext()));
            filteredEvents = new ArrayList<>(events);
        } else {
            events = savedInstanceState.getParcelableArrayList("events");
            filteredEvents = savedInstanceState.getParcelableArrayList("filteredEvents");
        }
        swipeCounter = new HashMap<>();
        acceptedEvents = new ArrayList<>();
        rejectedEvents = new ArrayList<>();
    }

    @Override
    public void receiveSwipedOutCard(EventCard card) {
        String name = card.getCardEventName();
        if (resolveFilterCreation(name)) {
            FilterEventsDialogFragment.newInstance(name, this).show(Objects.requireNonNull(getFragmentManager()), "AlertDialog");
        }
        events.remove(card.getEventModel());
        filteredEvents.remove(card.getEventModel());
        rejectedEvents.add(card.getEventModel());
    }

    private boolean resolveFilterCreation(String name) {
        if (swipeCounter.containsKey(name)) {
            swipeCounter.put(name, (swipeCounter.get(name) + 1));
        } else {
            swipeCounter.put(name, 1);
        }
        return swipeCounter.get(name) == 3;
    }

    @Override
    public void receiveSwipedInCard(EventCard card) {
        String name = card.getCardEventName();
        if (swipeCounter.containsKey(name)) {
            swipeCounter.put(name, 0);
        }
        events.remove(card.getEventModel());
        filteredEvents.remove(card.getEventModel());
        acceptedEvents.add(card.getEventModel());
    }

    @Override
    public void onDialogPositiveClick(FilterEventsDialogFragment dialog) {
        mSwipeView.removeAllViews();
        Context mContext = Objects.requireNonNull(getActivity()).getApplicationContext();
        filteredEvents = filteredEvents.stream()
                .filter(eventModel -> !eventModel.getName().equals(dialog.getEventName()))
                .peek(event -> mSwipeView.addView(new EventCard(mContext, event, mSwipeView, this)))
                .collect(Collectors.toList());

    }

    @Override
    public void onDialogNegativeClick(FilterEventsDialogFragment dialog) {

    }

    @Override
    public void onStop() {
        super.onStop();
        saveFilteredEvents();

    }

    private void saveFilteredEvents() {
        //TODO: Send filtered (rejected/accepted) events to backend
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        ArrayList<Parcelable> saveList = new ArrayList<>(events);
        ArrayList<Parcelable> saveFilteredList = new ArrayList<>(filteredEvents);
        outState.putParcelableArrayList("events", saveList);
        outState.putParcelableArrayList("filteredEvents", saveFilteredList);
        super.onSaveInstanceState(outState);
    }
}
