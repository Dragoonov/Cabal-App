package com.cabal.app.utils

import com.cabal.app.database.entities.Event

object Filters {
        var AUTHOR_FILTER = false
        var GUEST_FILTER = false
        var FINISHED_FILTER = false

    fun performFiltering(list: List<Event>): List<Event> {
        val resultList = ArrayList<Event>(list)
        val modelsToRemove = ArrayList<Event>()
        //if (AUTHOR_FILTER) list.forEach{ if (it.creator != UserManager.loggedUser?.nick) modelsToRemove.add(it) }
        //if (GUEST_FILTER) list.forEach{ if (it.creator == UserManager.loggedUser?.nick) modelsToRemove.add(it) }
        //if (FINISHED_FILTER) list.forEach{ if (it.isFinished) modelsToRemove.add(it) }
        resultList.removeAll(modelsToRemove)
        return resultList
    }
}