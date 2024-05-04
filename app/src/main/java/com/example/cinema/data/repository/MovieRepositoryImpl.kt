package com.example.cinema.data.repository

import com.example.cinema.data.local.LocalDataSource
import com.example.cinema.data.remote.RemoteDataSource
import com.example.cinema.domain.model.Genre
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails
import com.example.cinema.domain.repository.MovieRepository


class MovieRepositoryImpl(private val remoteDataSource : RemoteDataSource,private val localDataSource: LocalDataSource) : MovieRepository {


    override suspend fun loadMovies(page : Int): List<Movie> {
        return remoteDataSource.loadMovies(page)
    }

    override suspend fun loadMovie(movieId: Int) : MovieDetails {

        val request = try {
            val movieDetails = localDataSource.getMovie(movieId)
            movieDetails
        }
        catch (e: NoSuchElementException) {
            val movieDetails = remoteDataSource.loadMovie(movieId)
            localDataSource.insertMovieDetails(movieDetails)
            movieDetails
        }
        return request
    }

    override suspend fun loadGenres(): List<Genre> {
        return remoteDataSource.loadGenres()
    }
}