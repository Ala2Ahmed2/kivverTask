package com.example.citysearch.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val country: String,
    val name: String,
    val _id: Int,
    val coord: Coord
)
