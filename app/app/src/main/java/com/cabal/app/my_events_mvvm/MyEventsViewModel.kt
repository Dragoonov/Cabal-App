package com.cabal.app.my_events_mvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cabal.app.database.entities.Event
import com.cabal.app.database.repository.Repository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MyEventsViewModel @Inject constructor(app:Application, repository: Repository) : AndroidViewModel(app){

    val events:MutableLiveData<List<Event>> = MutableLiveData()

    init {
        repository.getEventsByAccepted(true)
                .subscribeOn(Schedulers.io())
                .subscribe(
                {events.value = it},
                { Log.d("MyEventsViewModel",it.localizedMessage!!)}
        ).dispose()
    }
}