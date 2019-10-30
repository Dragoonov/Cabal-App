package com.cabal.app.navigation_bar.search_events;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cabal.app.R;
import com.cabal.app.models.EventModel;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@NonReusable
@Layout(R.layout.fragment_search_item)
public class EventCard {

    @View(R.id.cardEventImage)
    private ImageView cardEventImage;

    @View(R.id.cardEventName)
    private TextView cardEventName;

    @View(R.id.cardEventCreator)
    private TextView cardEventCreator;

    @View(R.id.cardEventDate)
    private TextView cardEventDate;

    @View(R.id.cardEventMembers)
    private RecyclerView cardEventMembers;

    @View(R.id.cardEventLocation)
    private TextView cardEventLocation;

    @View(R.id.cardEventDescription)
    private TextView cardEventDescription;

    private EventModel eventModel;
    private Context context;
    private SwipePlaceHolderView swipePlaceHolderView;

    public EventCard(Context context, EventModel eventModel, SwipePlaceHolderView swipeView) {
        this.context = context;
        this.eventModel = eventModel;
        swipePlaceHolderView = swipeView;
    }

    @Resolve
    private void onResolved() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cardEventMembers.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new EventCardsAdapter(eventModel.getMembers());
        cardEventMembers.setAdapter(adapter);
        Glide.with(context).load(eventModel.getImage()).into(cardEventImage);
        cardEventName.setText(eventModel.getName());
        cardEventCreator.setText(eventModel.getCreator());
        cardEventDate.setText(eventModel.getDate());
        cardEventLocation.setText(eventModel.getLocation());
        cardEventDescription.setText(eventModel.getDescription());
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }
}