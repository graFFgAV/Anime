package com.graffgaaav.animeshnik.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "topAnime")
data class TopMovie (

    @ColumnInfo(name = "mal_id")
    val mal_id :Int ,

    @PrimaryKey
    @ColumnInfo(name = "title")
    val title : String ,

    @ColumnInfo(name = "image_url")
    val image_url : String ,

    @ColumnInfo(name = "score")
    val score :Float ,

    @ColumnInfo(name = "type")
    val type : String
): Serializable