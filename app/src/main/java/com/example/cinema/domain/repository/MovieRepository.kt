package com.example.cinema.domain.repository

import com.example.cinema.domain.model.Genre
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

interface MovieRepository {

    suspend fun loadMovies(page : Int) : List<Movie>
    suspend fun loadMovie(movieId: Int) : MovieDetails

    suspend fun loadGenres() : List<Genre>
}