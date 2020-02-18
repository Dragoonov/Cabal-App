package com.cabal.app.search_mvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cabal.app.MyApplication
import com.cabal.app.R
import com.cabal.app.dialogs.FilterEventsDialogFragment
import com.cabal.app.navigation_bar.search_events.EventCard
import com.cabal.app.utils.SwipeType
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import javax.inject.Inject

class SearchFragment : Fragment(), EventCard.SwipeListener, FilterEventsDialogFragment.AlertDialogListener {

    private lateinit var mSwipeView: SwipePlaceHolderView
    private val CARDS_COUNT = 5

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as MyApplication).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this,viewModelFactory)[SearchViewModel::class.java]
        mSwipeView = view.findViewById(R.id.swipeView)

        mSwipeView.getBuilder<SwipePlaceHolderView,SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(CARDS_COUNT)
                .setSwipeType(SwipeType.DEFAULT)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_accept)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_reject))

       // viewModel.filteredEvents.value?.forEach{mSwipeView.addView(EventCard(context!!,it,mSwipeView,this))}
        viewModel.filteredEvents.observe(this, Observer { it1 ->
            mSwipeView.removeAllViews()
            it1.forEach {mSwipeView.addView(EventCard(context!!,it,mSwipeView,this))}
            Log.d("SWIPE", mSwipeView.childCount.toString())
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveEvents()
    }

    override fun receiveSwipedOutCard(card: EventCard) = viewModel.receiveSwipedOutCard(card)


    override fun receiveSwipedInCard(card: EventCard) = viewModel.receiveSwipedInCard(card)

    override fun onDialogPositiveClick(dialogFragment: FilterEventsDialogFragment) {
        viewModel.filterEvents(dialogFragment.eventName!!)
    }

    override fun onDialogNegativeClick(dialogFragment: FilterEventsDialogFragment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}