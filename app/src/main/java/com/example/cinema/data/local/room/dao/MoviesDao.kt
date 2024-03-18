package com.example.cinema.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cinema.data.local.room.entities.ActorEntity
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieDetailsEntity
import com.example.cinema.data.local.room.entities.MovieEntity
import com.example.cinema.data.local.room.entities.relations.MovieDetailsWithGenresAndActors
import com.example.cinema.data.local.room.entities.relations.MovieWithGenres

@Dao
interface MoviesDao {
//    @Transaction
//    @Query("SELECT * FROM ${MovieEntity.MOVIE_TABLE_NAME}")
//    suspend fun getMovies() : List<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActors(actor: ActorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movieDetailsEntity: MovieDetailsEntity)

    @Transaction
    @Query("SELECT * FROM ${MovieDetailsEntity.TABLE_NAME} WHERE movieDetailsId = :movieDetailsId")
    suspend fun getMovieDetails(movieDetailsId : Int) : List<MovieDetailsWithGenresAndActors>

}