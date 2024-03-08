package com.example.cinema.data

import com.example.cinema.data.local.LocalDataSource
import com.example.cinema.data.remote.RemoteDataSource
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails
import com.example.cinema.domain.repository.MovieRepository


class MovieRepositoryImpl(private val remoteDataSource : RemoteDataSource,private val localDataSource: LocalDataSource) : MovieRepository {

    override suspend fun loadMovies(): List<Movie> {

//        val movieDb = localDataSource.getMovies()
//        if (movieDb.isEmpty()) {
//            val movieFromNetwork = remoteDataSource.loadMovies()
//            localDataSource.insertMovies(movieFromNetwork)
//            return movieFromNetwork
//        }
//        else return movieDb
        return remoteDataSource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int) : MovieDetails {
        return remoteDataSource.loadMovie(movieId)
    }
}