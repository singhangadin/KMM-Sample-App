package com.github.angads25.kmmsampleapp.data

import kotlinx.serialization.Serializable

@Serializable
data class PixabayImageResponse (
    @Serializable var total : Int,
    @Serializable var totalHits : Int,
    @Serializable var hits : List<Hits>
)

@Serializable
data class Hits (
    @Serializable var id : Int,
    @Serializable var pageURL : String,
    @Serializable var type : String,
    @Serializable var tags : String,
    @Serializable var previewURL : String,
    @Serializable var previewWidth : Int,
    @Serializable var previewHeight : Int,
    @Serializable var webformatURL : String,
    @Serializable var webformatWidth : Int,
    @Serializable var webformatHeight : Int,
    @Serializable var largeImageURL : String,
    @Serializable var fullHDURL : String?=null,
    @Serializable var imageURL : String?=null,
    @Serializable var imageWidth : Int,
    @Serializable var imageHeight : Int,
    @Serializable var imageSize : Int,
    @Serializable var views : Int,
    @Serializable var downloads : Int,
    @Serializable var likes : Int,
    @Serializable var comments : Int,
    @Serializable var userId : Int?=null,
    @Serializable var user : String,
    @Serializable var userImageURL : String
)