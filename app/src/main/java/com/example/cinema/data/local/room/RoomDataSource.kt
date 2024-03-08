package com.example.cinema.data.local.room

import com.example.cinema.data.local.LocalDataSource
import com.example.cinema.data.local.room.dao.MoviesDao
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieEntity
import com.example.cinema.data.remote.retrofit.ImageUrlAppender
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Genre
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

class RoomDataSource(private val moviesDao: MoviesDao) : LocalDataSource { // TODO() сделать получение инстанса db вместо dao

    override suspend fun getMovies(): List<Movie> {

        return moviesDao.getMovies().map {
            Movie(
                id = it.movie.id,
                title = it.movie.title,
                rating = it.movie.rating,
                runningTime = it.movie.runningTime,
                reviewCount = it.movie.reviewCount,
                pgAge = it.movie.pgAge,
                isLiked = false,
                imageUrl = it.movie.imageUrl,
                genres = it.genres.map { genreEntity ->  Genre(genreEntity.id,genreEntity.name) }
            )
        }
    }

    override suspend fun insertMovies(movieFromNetwork: List<Movie>) {
        moviesDao.insertMovies(
            movieFromNetwork.map {
                MovieEntity(
                    id = it.id,
                    pgAge = it.pgAge,
                    title = it.title,
                    runningTime = it.runningTime,
                    reviewCount = it.reviewCount,
                    isLiked = false,
                    rating = it.rating,
                    imageUrl = it.imageUrl
                )
            }
        )
        val genreEntities = mutableListOf<GenreEntity>()
        movieFromNetwork.forEach { movie ->
            movie.genres.forEach { genre ->
                genreEntities.add(GenreEntity(genre.id, genre.name, movie.id))
            }
        }
        moviesDao.insertGenres(genreEntities)
    }
//    override suspend fun getMovie(movieId: Int): MovieDetails {
//        val movieDetailsWithActorsAndGenres = moviesDao.getMovie() ?: throw IllegalStateException("Movie details are null")
//
//        return MovieDetails(
//            id = movieDetailsWithActorsAndGenres.movieDetails.parentId,
//            pgAge = movieDetailsWithActorsAndGenres.movieDetails.pgAge,
//            title = movieDetailsWithActorsAndGenres.movieDetails.title,
//            genres = movieDetailsWithActorsAndGenres.genres.map { genreEntity -> Genre(genreEntity.genreId, genreEntity.name) },
//            reviewCount = movieDetailsWithActorsAndGenres.movieDetails.reviewCount,
//            isLiked = false,
//            rating = movieDetailsWithActorsAndGenres.movieDetails.rating,
//            detailImageUrl = movieDetailsWithActorsAndGenres.movieDetails.detailImageUrl,
//            storyLine = movieDetailsWithActorsAndGenres.movieDetails.storyLine,
//            actors = movieDetailsWithActorsAndGenres.actors.map { actor ->
//                Actor(
//                    actorId = actor.actorId,
//                    name = actor.name,
//                    imageUrl = actor.imageUrl
//                )
//            }
//        )
//    }
//    override fun insertMovieDetails(movieDetailsFromNetwork: MovieDetails) {
//        val movieDetailsEntity = movieDetailsFromNetwork.let {
//            MovieDetailsEntity(
//                parentId = it.id,
//                pgAge = it.pgAge,
//                title = it.title,
//                reviewCount = it.reviewCount,
//                isLiked = false,
//                rating = it.rating,
//                detailImageUrl = it.detailImageUrl,
//                storyLine = it.storyLine
//            )
//        }
//        movieDetailsFromNetwork.genres.forEach {
//            val genreEntity = GenreEntity(
//                genreId = it.id,
//                name = it.name,
//                parentId = movieDetailsFromNetwork.id
//            )
//            moviesDao.insertGenres(genreEntity)
//        }
//        movieDetailsFromNetwork.actors.forEach {
//            val actorEntity = ActorEntity(
//                actorId = it.actorId,
//                name = it.name,
//                imageUrl = it.imageUrl,
//                parentId = movieDetailsFromNetwork.id
//            )
//            moviesDao.insertActors(actorEntity)
//        }
//        moviesDao.insertMovieDetails(movieDetailsEntity)
//    }
}