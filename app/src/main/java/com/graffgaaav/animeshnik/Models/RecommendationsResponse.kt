package com.graffgaaav.animeshnik.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecommendationsResponse (
    @Expose
    @SerializedName("recommendations")
    val recommendations : ArrayList<AnimeRecommendation>
): Serializable