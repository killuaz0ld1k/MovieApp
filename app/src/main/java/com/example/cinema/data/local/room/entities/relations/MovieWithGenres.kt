package com.example.cinema.data.local.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieEntity

data class MovieWithGenres (

    @Embedded val movie: MovieEntity,
    @Relation(parentColumn = "id", entityColumn = "parentId")
    val genres: List<GenreEntity>
)