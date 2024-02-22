package com.example.cinema.data.remote.retrofit

import com.example.cinema.data.remote.RemoteDataSource
import com.example.cinema.data.remote.retrofit.response.MovieDetailsResponse
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Genre
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.model.MovieDetails

internal class RetrofitDataSource(private val moviesApi: MoviesApi, private val imageUrlAppender : ImageUrlAppender) : RemoteDataSource {

    override suspend fun loadMovies(): List<Movie> {

        val genres = moviesApi.loadGenres().genres

        // TODO из ViewModel передать номер страницы

        return moviesApi.loadUpcoming(page = 1).results.map { movieResponse ->
            println("Movie Response: $movieResponse")
            Movie(
                id = movieResponse.id,
                title = movieResponse.title,
                rating = movieResponse.voteAverage.toInt(),
                runningTime = 120,
                reviewCount = movieResponse.voteCount,
                pgAge = if (movieResponse.adult) 16 else 13,
                isLiked = false,
                imageUrl = imageUrlAppender.getMoviePosterImageUrl(movieResponse.posterPath),
                genres = genres
                    .filter { genreResponse -> movieResponse.genreIds.contains(genreResponse.id) } // Todo разобраться почему выдаёт ошибку
                    .map { genre -> Genre(genre.id, genre.name) }
                // что тут за хуйня происходит ??????
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {

        val details = moviesApi.loadMovieDetails(movieId)

        return MovieDetails(
            id = details.id,
            pgAge = if (details.adult) 16 else 13,
            title = details.title,
            genres = details.genres.map { Genre(it.id, it.name) },
            reviewCount = details.revenue,
            isLiked = false,
            rating = details.popularity.toInt(),
            detailImageUrl = imageUrlAppender.getDetailImageUrl(details.backdropPath),
            storyLine = details.overview.orEmpty(),
            actors = moviesApi.loadMovieCredits(movieId).cast.map { actor ->
                Actor(
                    actorId = actor.id,
                    name = actor.name,
                    imageUrl = (if (imageUrlAppender.getActorImageUrl(actor.profile_path) != null) imageUrlAppender.getActorImageUrl(actor.profile_path) else "https://sopranoclub.ru/images/memy-na-avu-275-memnyh-avatarok/file56870.jpeg").toString()
                )
            }
        )

    }
}