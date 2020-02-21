package com.cabal.app.search_mvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cabal.app.database.entities.Event
import com.cabal.app.database.repository.Repository
import com.cabal.app.navigation_bar.search_events.EventCard
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.stream.Collectors
import javax.inject.Inject

class SearchViewModel @Inject constructor(app: Application,private val repository: Repository) : AndroidViewModel(app) {

    private lateinit var events: MutableList<Event>
    var filteredEvents: MutableLiveData<MutableList<Event>> = MutableLiveData()
    private var swipedEvents: MutableList<Event> = ArrayList<Event>().toMutableList()
    private var swipeCounter: MutableMap<String, Int> = HashMap()
    var dialogNotifier: MutableLiveData<String> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        loadEvents()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun receiveSwipedOutCard(card: EventCard) {
        val name = card.getCardEventName()!!
        if (resolveFilterCreation(name))
            showFilterDialog(name)
        events.remove(card.getEventModel())
        filteredEvents.value?.remove(card.getEventModel())
        swipedEvents.add(card.getEventModel().also { it.accepted = false })

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
        swipedEvents.add(model.also { it.accepted = true })

        if (filteredEvents.value!!.isEmpty())
            loadEvents()
    }

    private fun resolveFilterCreation(name: String): Boolean {
        if (swipeCounter.containsKey(name)) {
            swipeCounter[name] = swipeCounter[name]!!.inc()
        } else {
            swipeCounter[name] = 1
        }
        return swipeCounter[name] == 3
    }


    fun showFilterDialog(name: String?) {
        dialogNotifier.value = name
    }

    fun filterEvents(name: String) {
        filteredEvents.value = filteredEvents.value?.stream()!!
                .filter{it.name != name }
                .collect(Collectors.toList())
    }


    private fun loadEvents() {
        repository.getEvents()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    events = it.toMutableList()
                    filteredEvents.postValue(events.toMutableList())
                    Log.d("Czy ma aktywnych observerow: ", filteredEvents.hasActiveObservers().toString())
                }, {
                    Log.d("SearchViewModel", it.localizedMessage!!)
                }
        ).also {
                    compositeDisposable.add(it)
                }
    }

    fun saveEvents() = repository.insertEvents(swipedEvents)

}