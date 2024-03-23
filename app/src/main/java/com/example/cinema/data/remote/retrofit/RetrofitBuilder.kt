package com.example.cinema.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitBuilder @Inject constructor(
        private val okHttpClient: OkHttpClient
) {
    private val baseUrl = "https://api.themoviedb.org/"
    private val version = "3/"
    private val contentType = "application/json".toMediaType()

    private val json = Json { ignoreUnknownKeys = true }
    private val converterFactory = json.asConverterFactory(contentType)

    fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl + version)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build()
    }
}
