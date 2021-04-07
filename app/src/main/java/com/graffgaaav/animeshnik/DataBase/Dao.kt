package com.graffgaaav.animeshnik.DataBase

import androidx.room.*
import androidx.room.Dao
import com.graffgaaav.animeshnik.Models.FavoriteModel
import io.reactivex.Single

@Dao
interface Dao {

    @get:Query("SELECT * FROM favorite")
    val all: Single<List<FavoriteModel>>

    @Insert
    fun insert(fm: FavoriteModel?)

    @Delete
    fun delete(fm: FavoriteModel?)

    @Update
    fun update(fm: FavoriteModel?)
}