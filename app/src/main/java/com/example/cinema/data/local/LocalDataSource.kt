package com.example.cinema.data.local

import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

interface LocalDataSource {

    suspend fun getMovie(movieId: Int) : MovieDetails

    suspend fun insertMovieDetails(movieDetailsFromNetwork : MovieDetails)
}