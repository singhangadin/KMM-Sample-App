package com.github.angads25.kmmsampleapp.data

import kotlinx.serialization.*

@Serializable
data class PexelImageResponse (
    @Serializable val total_results : Int,
    @Serializable val page : Int,
    @Serializable val per_page : Int,
    @Serializable val photos : List<Photos>,
    @Serializable val next_page : String
)

@Serializable
data class Photos (
    @Serializable val id : Int,
    @Serializable val width : Int,
    @Serializable val height : Int,
    @Serializable val url : String,
    @Serializable val photographer : String,
    @Serializable val photographer_url : String,
    @Serializable val photographer_id : Int,
    @Serializable val avg_color : String,
    @Serializable val src : Src,
    @Serializable val liked : Boolean
)

@Serializable
data class Src (
    @Serializable val original : String,
    @Serializable val large2x : String,
    @Serializable val large : String,
    @Serializable val medium : String,
    @Serializable val small : String,
    @Serializable val portrait : String,
    @Serializable val landscape : String,
    @Serializable val tiny : String
)