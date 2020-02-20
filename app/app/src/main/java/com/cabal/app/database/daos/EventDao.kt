package com.cabal.app.database.daos

import androidx.room.*
import com.cabal.app.database.entities.Event
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface EventDao {

    @Query("select * from event where name = :name")
    fun getEventByName(name: String): Single<Event>

    @Query("select * from event where accepted = :accepted")
    fun getEventsByAccepted(accepted: Boolean): Single<List<Event>>

    @Query("select * from event")
    fun getEvents(): List<Event>

    @Update
    fun updateEvent(event: Event)

    @Insert
    fun insertEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<Event>): Completable

    @Delete
    fun deleteEvent(event: Event)
}