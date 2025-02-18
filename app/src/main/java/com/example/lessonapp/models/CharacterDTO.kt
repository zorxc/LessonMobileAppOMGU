package com.example.lessonapp.models

object CharacterMapper {
    fun dtoToEntity(dto: Character): CharacterEntity = with(dto) {
        CharacterEntity(
            id = id,
            name = name,
            description = description,
            thumbnailPath = thumbnail.path,
            thumbnailExtension = thumbnail.extension
        )
    }
}

data class CharacterUI(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnailUrl: String
)

fun CharacterEntity.toUI(): CharacterUI = CharacterUI(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = "$thumbnailPath.$thumbnailExtension"
)

fun Character.toUI(): CharacterUI = CharacterUI(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = thumbnail.fullPath()
)
