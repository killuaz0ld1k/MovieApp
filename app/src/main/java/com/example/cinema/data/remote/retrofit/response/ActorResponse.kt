package com.example.cinema.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class ActorResponse (
    @SerialName("id") val id : Int,
    @SerialName("name") val name : String,
    @SerialName("profile_path") val profile_path : String?)

/*
val actorId : Int,
    val imageUrl : String?,
    val name : String
 */