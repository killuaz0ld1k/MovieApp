package com.example.cinema.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponse(
    @SerialName("genres") val genres: List<GenreResponse>
)