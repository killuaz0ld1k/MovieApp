package com.example.cinema.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActorsResponse
(
    @SerialName("id") val id : Int,
    @SerialName("cast") val cast : List<ActorResponse>
)