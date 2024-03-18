package com.example.cinema.data.local.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cinema.data.local.room.entities.ActorEntity
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieDetailsEntity

data class MovieDetailsWithGenresAndActors(
    @Embedded val movieDetails : MovieDetailsEntity,
    @Relation(parentColumn = "movieDetailsId" , entityColumn = "movieDetailsId")
    val genres : List<GenreEntity>,
    @Relation(parentColumn = "movieDetailsId" , entityColumn = "movieDetailsId")
    val actors : List<ActorEntity>
)
