package com.cabal.app.search_mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cabal.app.database.entities.Event
import com.cabal.app.database.repository.Repository
import com.cabal.app.navigation_bar.search_events.EventCard
import com.cabal.app.utils.StaticDataGenerator
import java.util.stream.Collectors
import javax.inject.Inject

class SearchViewModel @Inject constructor(app: Application,private val repository: Repository) : AndroidViewModel(app) {

    private lateinit var events: MutableList<Event>
    var filteredEvents: MutableLiveData<MutableList<Event>> = MutableLiveData()
    private var acceptedEvents: MutableList<Event> = ArrayList<Event>().toMutableList()
    private var rejectedEvents: MutableList<Event> = ArrayList<Event>().toMutableList()
    private var swipeCounter: MutableMap<String, Int> = HashMap()

    init {
        loadEvents()
    }

    fun receiveSwipedOutCard(card: EventCard) {
        val name = card.getCardEventName()!!
        if (resolveFilterCreation(name))
            showFilterDialog(name)
        events.remove(card.getEventModel())
        filteredEvents.value?.remove(card.getEventModel())
        rejectedEvents.add(card.getEventModel())

        if (filteredEvents.value!!.isEmpty())
            loadEvents()
    }

    fun receiveSwipedInCard(card: EventCard) {
        val name = card.getCardEventName()!!
        val model = card.getEventModel()
        if (swipeCounter.containsKey(name)) {
            swipeCounter[name] = 0
        }
        events.remove(model)
        filteredEvents.value?.remove(model)
        acceptedEvents.add(model)

        if (filteredEvents.value!!.isEmpty())
            loadEvents()
    }

    private fun resolveFilterCreation(name: String): Boolean {
        if (swipeCounter.containsKey(name)) {
            swipeCounter[name]?.plus(1)
        } else {
            swipeCounter[name] = 1
        }
        return swipeCounter[name] == 3
    }


    fun showFilterDialog(name: String?) {

    }

    fun filterEvents(name: String) {
        filteredEvents.value = filteredEvents.value?.stream()!!
                .filter{it.name != name }
                .collect(Collectors.toList())
    }


    private fun loadEvents() {
        events = StaticDataGenerator.generateEvents(5).toMutableList()
        filteredEvents.value = events.toMutableList()
    }

    fun saveEvents() {
       // repository
    }


}