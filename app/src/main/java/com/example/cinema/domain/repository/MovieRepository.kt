package com.example.cinema.domain.repository

import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

interface MovieRepository {

    suspend fun loadMovies() : List<Movie>

    suspend fun loadMovie(movieId: Int) : MovieDetails
}