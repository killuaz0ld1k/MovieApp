package com.example.cinema.di.hilt

import com.example.cinema.data.MovieRepositoryImpl
import com.example.cinema.data.remote.RemoteDataSource
import com.example.cinema.data.remote.retrofit.ImageUrlAppender
import com.example.cinema.data.remote.retrofit.MoviesApi
import com.example.cinema.data.remote.retrofit.RetrofitDataSource
import com.example.cinema.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideImageUrlAppender(moviesApi: MoviesApi) : ImageUrlAppender = ImageUrlAppender(moviesApi)

    @Provides
    @Singleton
    fun provideRemoteDataSource(moviesApi: MoviesApi, imageUrlAppender: ImageUrlAppender) : RemoteDataSource = RetrofitDataSource(moviesApi,imageUrlAppender)

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: RemoteDataSource) : MovieRepository {
        return MovieRepositoryImpl(remoteDataSource)
    }
}
