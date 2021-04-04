package com.graffgaaav.animeshnik.Models

import java.io.Serializable


data class AnimeDetails (
    var mal_id : Int?,
    var image_url : String?,
    var title : String?,
    var type: String?,
    val trailer_url: String?,
    val episodes : Int?,
    //var aired : String? ,
    var score : Number? ,
    var scored_by : Int? ,
    var synopsis : String?,
    var premiered : String? ,
    var status : String? ,
    var source : String? ,
    val duration : String?,
    val genres : List<AnimeGenre>?
    ) : Serializable
