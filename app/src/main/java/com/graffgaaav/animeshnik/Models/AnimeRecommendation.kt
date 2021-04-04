package com.graffgaaav.animeshnik.Models

import java.io.Serializable

data class AnimeRecommendation (
    val mal_id : Int,
    val title : String,
    val image_url : String
): Serializable