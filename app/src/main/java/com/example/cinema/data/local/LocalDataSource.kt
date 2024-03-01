package com.example.cinema.data.local

import com.example.cinema.data.local.room.entities.MovieDetailsEntity
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

interface LocalDataSource {

    suspend fun getMovies() : List<Movie>

    fun insertMovies(movieFromNetwork : List<Movie>)

    suspend fun getMovie(movieId: Int) : MovieDetails

    fun insertMovieDetails(movieDetailsFromNetwork : MovieDetails)
}