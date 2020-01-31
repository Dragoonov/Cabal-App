package com.cabal.app.database.daos

import androidx.room.*
import com.cabal.app.database.entities.Hobby

@Dao
interface HobbyDao {

    @Query("select * from hobby where name = :name")
    fun getHobbyByName(name: String): Hobby

    @Update
    fun updateHobby(hobby: Hobby)

    @Insert
    fun insertHobby(hobby: Hobby)

    @Delete
    fun deleteHobby(hobby: Hobby)
}