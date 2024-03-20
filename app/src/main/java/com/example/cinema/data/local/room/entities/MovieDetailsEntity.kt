package com.example.cinema.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinema.data.local.room.entities.MovieDetailsEntity.Companion.TABLE_NAME
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Genre

@Entity(tableName = TABLE_NAME)
data class MovieDetailsEntity(
    @PrimaryKey(autoGenerate = false) val movieDetailsId: Int,
    val pgAge: Int,
    val title: String,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val detailImageUrl: String?,
    val storyLine: String
) {
    companion object {
        const val TABLE_NAME = "movie_details_entity"
    }
}
