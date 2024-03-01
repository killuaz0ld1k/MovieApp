package com.example.cinema.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinema.data.local.room.entities.MovieEntity.Companion.MOVIE_TABLE_NAME

@Entity(tableName = MOVIE_TABLE_NAME)
data class MovieEntity (
    @PrimaryKey val id: Int,
    val pgAge: Int,
    val title: String,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val imageUrl: String?
) {
    companion object {
        const val MOVIE_TABLE_NAME = "movies_table"
    }
}
