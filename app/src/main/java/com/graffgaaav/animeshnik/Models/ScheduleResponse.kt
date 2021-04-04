package com.graffgaaav.animeshnik.Models

import java.io.Serializable


data class ScheduleResponse (
    val monday : List<TopMovie> ,
    val tuesday : List<TopMovie> ,
    val wednesday : List<TopMovie> ,
    val thursday : List<TopMovie> ,
    val friday : List<TopMovie> ,
    val saturday : List<TopMovie> ,
    val sunday : List<TopMovie>
    ) : Serializable