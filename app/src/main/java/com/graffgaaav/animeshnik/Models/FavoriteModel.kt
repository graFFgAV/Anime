package com.graffgaaav.animeshnik.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class FavoriteModel(

        @PrimaryKey
        @ColumnInfo(name = "mal_id")
        val mal_id :Int,

        @ColumnInfo(name = "title")
        val title : String ,

        @ColumnInfo(name = "image_url")
        val image_url : String

) : Serializable


