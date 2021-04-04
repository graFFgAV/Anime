package com.graffgaaav.animeshnik.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TopMovieResponse(

    @Expose
    @SerializedName("top")
    val top: ArrayList<TopMovie>

) : Serializable