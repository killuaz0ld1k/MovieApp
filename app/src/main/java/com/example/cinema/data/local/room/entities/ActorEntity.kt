package com.example.cinema.data.local.room.entities

import androidx.room.Entity

@Entity
data class ActorEntity(
    val actorId : Int,
    val imageUrl : String,
    val name : String,
    val movieDetailsId : Int
)
