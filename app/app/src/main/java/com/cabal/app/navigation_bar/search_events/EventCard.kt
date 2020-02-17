package com.cabal.app.navigation_bar.search_events

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cabal.app.R
import com.cabal.app.database.entities.Event
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.NonReusable
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.*

@NonReusable
@Layout(R.layout.fragment_search_item)
class EventCard(private val context: Context, private val event: Event, swipeView: SwipePlaceHolderView, private val listener: SwipeListener) {

    interface SwipeListener {
        fun receiveSwipedOutCard(card: EventCard)
        fun receiveSwipedInCard(card: EventCard)
    }

    private val swipePlaceHolderView = swipeView

    @View(R.id.cardEventImage)
    private lateinit var cardEventImage: ImageView

    @View(R.id.cardEventName)
    private lateinit var cardEventName: TextView

    @View(R.id.cardEventCreator)
    private lateinit var cardEventCreator: TextView

    @View(R.id.cardEventDate)
    private lateinit var cardEventDate: TextView

    @View(R.id.cardEventMembers)
    private lateinit var cardEventMembers: RecyclerView

    @View(R.id.cardEventLocation)
    private lateinit var cardEventLocation: TextView

    @View(R.id.cardEventDescription)
    private lateinit var cardEventDescription: TextView

    fun getCardEventName() = event.name

    fun getEventModel() = event

    @Resolve
    private fun onResolved() {
        with(cardEventMembers, {
            layoutManager = LinearLayoutManager(context)
            adapter = EventCardsAdapter(event.members!!)
        })
        Glide.with(context).load(event.image).into(cardEventImage)
        cardEventName.text = event.name
        cardEventCreator.text = event.creator
        cardEventDate.text = event.date.toString()
        cardEventLocation.text = event.location
        cardEventDescription.text = event.description
    }

    @SwipeOut
    private fun onSwipedOut() = listener.receiveSwipedOutCard(this)

    @SwipeCancelState
    private fun onSwipeCancelState() = Log.d("EVENT", "onSwipeCancelState")

    @SwipeIn
    private fun onSwipeIn() = listener.receiveSwipedInCard(this)

    @SwipeInState
    private fun onSwipeInState() = Log.d("EVENT", "onSwipeInState")

    @SwipeOutState
    private fun onSwipeOutState() = Log.d("EVENT", "onSwipeOutState")
}