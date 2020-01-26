package com.cabal.app.database.daos

import androidx.room.*
import com.cabal.app.database.entities.User

@Dao
interface UserDao {

    @Query("select * from user where id = :id")
    fun getUserById(id: Int): User

    @Update
    fun updateUser(user: User)

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}