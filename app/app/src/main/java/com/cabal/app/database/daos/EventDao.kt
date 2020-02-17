package com.cabal.app.database.daos

import androidx.room.*
import com.cabal.app.database.entities.Event

@Dao
interface EventDao {

    @Query("select * from event where name = :name")
    fun getEventByName(name: String): Event

    @Update
    fun updateEvent(event: Event)

    @Insert
    fun insertEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)
}