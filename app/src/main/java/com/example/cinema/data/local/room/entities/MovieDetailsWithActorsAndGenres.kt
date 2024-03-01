package com.example.cinema.data.local.room.entities

import androidx.room.Embedded
import androidx.room.Relation

class MovieDetailsWithActorsAndGenres (
    @Embedded val movieDetails: MovieDetailsEntity,
    @Relation(parentColumn = "id", entityColumn = "childActorId")
    val actors: List<ActorEntity>,
    @Relation(parentColumn = "id", entityColumn = "childGenreId")
    val genres: List<GenreEntity>
)