package com.example.cinema.data.remote.retrofit

import com.example.cinema.data.remote.retrofit.response.ActorsResponse
import com.example.cinema.data.remote.retrofit.response.ConfigurationResponse
import com.example.cinema.data.remote.retrofit.response.GenresResponse
import com.example.cinema.data.remote.retrofit.response.MovieDetailsResponse
import com.example.cinema.data.remote.retrofit.response.UpComingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/upcoming")
    suspend fun loadUpcoming(
        @Query("page") page: Int // поставить ограничение по инту
    ): UpComingResponse

    @GET("configuration")
    suspend fun loadConfiguration(): ConfigurationResponse

    @GET("genre/movie/list")
    suspend fun loadGenres(): GenresResponse

    @GET("movie/{movie_id}")
    suspend fun loadMovieDetails(@Path("movie_id") movie_id : Int) : MovieDetailsResponse

    @GET("https://api.themoviedb.org/3/movie/{movie_id}/credits")
    suspend fun loadMovieCredits(@Path("movie_id") movie_id : Int) : ActorsResponse
}