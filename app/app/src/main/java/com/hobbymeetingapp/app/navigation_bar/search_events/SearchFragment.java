package com.hobbymeetingapp.app.navigation_bar.search_events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hobbymeetingapp.app.models.EventModel;
import com.hobbymeetingapp.app.R;
import com.hobbymeetingapp.app.Utils.SwipeType;
import com.hobbymeetingapp.app.Utils.JsonLoader;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.Objects;


public class SearchFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipePlaceHolderView mSwipeView = view.findViewById(R.id.swipeView);
        Context mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeType(SwipeType.HORIZONTAL)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_accept)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_reject));


        for(EventModel eventModel : Objects.requireNonNull(JsonLoader.loadProfiles(getActivity().getApplicationContext()))){
            mSwipeView.addView(new EventCard(mContext, eventModel, mSwipeView));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
