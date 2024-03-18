package com.example.cinema.data.local.room

import com.example.cinema.data.local.LocalDataSource
import com.example.cinema.data.local.room.dao.MoviesDao
import com.example.cinema.data.local.room.entities.ActorEntity
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
                genres = it.genres.map { genreEntity ->  Genre(genreEntity.genreId,genreEntity.name) }
            )
        }
    }

    override suspend fun getMovie(movieId: Int): MovieDetails {

        val movieDetailsWithGenresAndActors = moviesDao.getMovieDetails(movieId).firstOrNull() ?: throw NoSuchElementException("MovieDetails with ID $movieId not found")

        val movieDetails = movieDetailsWithGenresAndActors.movieDetails
        val actors = movieDetailsWithGenresAndActors.actors.map { actorEntity -> Actor(actorId = actorEntity.actorId, imageUrl = actorEntity.imageUrl, name = actorEntity.name) }
        val genres = movieDetailsWithGenresAndActors.genres.map { genreEntity -> Genre(id = genreEntity.genreId, name = genreEntity.name) }

        return MovieDetails(
            id = movieDetails.movieDetailsId,
            title = movieDetails.title,
            pgAge = movieDetails.pgAge,
            rating = movieDetails.rating,
            reviewCount = movieDetails.reviewCount,
            storyLine = movieDetails.storyLine,
            isLiked = false,
            detailImageUrl = movieDetails.detailImageUrl,
            genres = genres,
            actors = actors
        )
    }

    override fun insertMovieDetails(movieDetailsFromNetwork: MovieDetails) {
        TODO("Not yet implemented")
    }

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