package com.example.cinema.data.local.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenres (

    @Embedded val movie: MovieEntity,
    @Relation(parentColumn = "id", entityColumn = "parentId")
    val genres: List<GenreEntity>
)