package com.cabal.app.database.daos

import androidx.room.*
import com.cabal.app.database.entities.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("select * from user where id = :id")
    fun getUserById(id: String): Single<User>

    @Query("select loggedIn from user where id = :id")
    fun checkIfUserLoggedIn(id: String): Single<Boolean>

    @Update
    fun updateUser(user: User): Completable

    @Insert
    fun insertUser(user: User): Completable

    @Delete
    fun deleteUser(user: User): Completable
}