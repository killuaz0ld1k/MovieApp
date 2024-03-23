package com.example.cinema.data.remote.retrofit.okhttp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OkHttpClientProvider {

    companion object {
        private const val API_KEY = "efb0a0c2d12454814c8751650f77feb4"
    }
    fun provideOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newUrl = originalRequest.url.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }
}