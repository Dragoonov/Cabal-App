package com.cabal.app.Utils

import com.cabal.app.models.EventModel

object Filters {
        var AUTHOR_FILTER = false
        var GUEST_FILTER = false
        var FINISHED_FILTER = false

    fun performFiltering(list: List<EventModel>): List<EventModel> {
        val resultList = ArrayList<EventModel>(list)
        val modelsToRemove = ArrayList<EventModel>()
        if (AUTHOR_FILTER) list.forEach{ if (it.creator != User.getNick()) modelsToRemove.add(it) }
        if (GUEST_FILTER) list.forEach{ if (it.creator == User.getNick()) modelsToRemove.add(it) }
        if (FINISHED_FILTER) list.forEach{ if (it.isFinished) modelsToRemove.add(it) }
        resultList.removeAll(modelsToRemove)
        return resultList
    }
}