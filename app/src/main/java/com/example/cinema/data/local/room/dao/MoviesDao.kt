package com.example.cinema.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieEntity
import com.example.cinema.data.local.room.entities.MovieWithGenres
import com.example.cinema.domain.model.Movie

@Dao
interface MoviesDao {

    @Transaction
    @Query("SELECT * FROM ${MovieEntity.MOVIE_TABLE_NAME}")
    suspend fun getMovies() : List<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies : Iterable<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genre: Iterable<GenreEntity>)

    @Delete
    fun deleteMovies(movies : List<MovieEntity>)
}