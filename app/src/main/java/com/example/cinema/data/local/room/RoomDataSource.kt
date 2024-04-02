package com.example.cinema.data.local.room

import com.example.cinema.data.local.LocalDataSource
import com.example.cinema.data.local.room.entities.ActorEntity
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieDetailsEntity
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Genre
import com.example.cinema.domain.model.MovieDetails

class RoomDataSource(private val db : RoomDataBase) : LocalDataSource {

    private val moviesDao = db.moviesDao()

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
        movieDetailsFromNetwork.actors.forEach() {
            moviesDao.insertActors(
                ActorEntity(
                    actorId = it.actorId,
                    imageUrl = it.imageUrl,
                    name = it.name,
                    movieDetailsId = movieDetailsFromNetwork.id
                )
            )
        }
        movieDetailsFromNetwork.genres.forEach() {
            moviesDao.insertGenres(
                GenreEntity(
                    genreId = it.id,
                    name = it.name,
                    movieDetailsId = movieDetailsFromNetwork.id
                )
            )
        }
        val movieDetailsEntity = movieDetailsFromNetwork.let {
            MovieDetailsEntity(
                movieDetailsId = it.id,
                pgAge = it.pgAge,
                title = it.title,
                reviewCount = it.reviewCount,
                isLiked = false,
                rating = it.rating,
                detailImageUrl = it.detailImageUrl,
                storyLine = it.storyLine
            )
        }
        moviesDao.insertMovieDetails(movieDetailsEntity)
    }
}