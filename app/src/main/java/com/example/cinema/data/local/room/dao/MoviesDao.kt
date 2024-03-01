package com.example.cinema.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cinema.data.local.room.entities.ActorEntity
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieDetailsEntity
import com.example.cinema.data.local.room.entities.MovieDetailsWithActorsAndGenres
import com.example.cinema.data.local.room.entities.MovieEntity
import com.example.cinema.data.local.room.entities.MovieWithGenres
import com.example.cinema.domain.model.MovieDetails

@Dao
interface MoviesDao {

    @Transaction
    @Query("SELECT * FROM ${MovieEntity.MOVIE_TABLE_NAME}")
    suspend fun getMovies() : List<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies : List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActors(actors: List<ActorEntity>)

    @Transaction
    @Query("SELECT * FROM ${MovieDetailsEntity.MOVIE_DETAILS_TABLE_NAME}")
    suspend fun getMovie() : MovieDetailsWithActorsAndGenres

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(item : MovieDetailsEntity)
}