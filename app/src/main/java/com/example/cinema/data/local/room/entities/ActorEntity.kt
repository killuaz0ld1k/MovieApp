package com.example.cinema.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinema.data.local.room.entities.ActorEntity.Companion.ACTOR_TABLE_NAME

@Entity(tableName = ACTOR_TABLE_NAME)
data class ActorEntity (
    @PrimaryKey val actorId : Int,
    val imageUrl : String,
    val name : String?,
    val childActorId : Int
) {
    companion object {
        const val ACTOR_TABLE_NAME = "actor_entity"
    }
}