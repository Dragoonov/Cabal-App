package com.cabal.app.utils

import com.cabal.app.database.entities.Event
import javax.inject.Inject
import javax.inject.Singleton


object Filters {
        var AUTHOR_FILTER = false
        var GUEST_FILTER = false
        var FINISHED_FILTER = false

    fun performFiltering(list: List<Event>): List<Event> {
        val resultList = ArrayList<Event>(list)
        val modelsToRemove = ArrayList<Event>()
        //if (AUTHOR_FILTER) list.forEach{ if (it.creator != userManager.loggedUser) modelsToRemove.add(it) }
        //if (GUEST_FILTER) list.forEach{ if (it.creator == userManager.loggedUser) modelsToRemove.add(it) }
        //if (FINISHED_FILTER) list.forEach{ if (it.finished!!) modelsToRemove.add(it) }
        resultList.removeAll(modelsToRemove)
        return resultList
    }
}