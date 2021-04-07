package com.graffgaaav.animeshnik.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.graffgaaav.animeshnik.Models.FavoriteModel


@Database(entities = [(FavoriteModel::class)],version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDataBase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "favorite-db")
                    .allowMainThreadQueries().build()
            }
            return INSTANCE as AppDatabase
        }
    }

    abstract fun dao(): Dao
}