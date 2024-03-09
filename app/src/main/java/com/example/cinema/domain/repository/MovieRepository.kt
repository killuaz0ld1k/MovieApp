package com.example.cinema.domain.repository

import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

interface MovieRepository {

    suspend fun loadMovies(page : Int) : List<Movie>

    suspend fun loadMovie(movieId: Int) : MovieDetails
}