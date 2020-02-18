package com.cabal.app.database.daos

import androidx.room.*
import com.cabal.app.database.entities.Event

@Dao
interface EventDao {

    @Query("select * from event where name = :name")
    fun getEventByName(name: String): Event

    @Query("select * from event")
    fun getEvents(): List<Event>

    @Update
    fun updateEvent(event: Event)

    @Insert
    fun insertEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<Event>)

    @Delete
    fun deleteEvent(event: Event)
}