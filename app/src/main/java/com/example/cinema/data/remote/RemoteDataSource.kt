package com.example.cinema.data.remote

import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

interface RemoteDataSource {
    suspend fun loadMovies(page : Int) : List<Movie>
    suspend fun loadMovie(movieId : Int) : MovieDetails
}