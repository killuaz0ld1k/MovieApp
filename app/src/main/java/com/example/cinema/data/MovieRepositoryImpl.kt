package com.example.cinema.data

import com.example.cinema.data.remote.RemoteDataSource
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails
import com.example.cinema.domain.repository.MovieRepository


class MovieRepositoryImpl(private val remoteDataSource : RemoteDataSource) : MovieRepository {

    override suspend fun loadMovies(): List<Movie> {
        return remoteDataSource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int) : MovieDetails {
        return remoteDataSource.loadMovie(movieId)
    }
}