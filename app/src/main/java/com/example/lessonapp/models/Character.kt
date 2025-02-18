package com.example.lessonapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelResponse(
    val code: Int,
    val data: Data
)

@JsonClass(generateAdapter = true)
data class Data(
    val results: List<Character>
)

@JsonClass(generateAdapter = true)
data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)

@JsonClass(generateAdapter = true)
data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun fullPath() = "$path.$extension".replace("http://", "https://")
}
