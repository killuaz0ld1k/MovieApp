package com.example.cinema.di.hilt

import com.example.cinema.data.remote.retrofit.MoviesApi
import com.example.cinema.data.remote.retrofit.RetrofitBuilder
import com.example.cinema.data.remote.retrofit.okhttp.OkHttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = RetrofitBuilder(okHttpClient).buildRetrofit()

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient = OkHttpClientProvider().provideOkHttpClient()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit) : MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }
}