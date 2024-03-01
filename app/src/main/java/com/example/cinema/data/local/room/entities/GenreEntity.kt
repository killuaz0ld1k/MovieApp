package com.example.cinema.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.cinema.data.local.room.entities.GenreEntity.Companion.GENRE_TABLE_ENTITY

@Entity(
    tableName = GENRE_TABLE_ENTITY
)
data class GenreEntity(
    @PrimaryKey val genreId: Int,
    val name: String,
    val childGenreId : Int
) {
    companion object {
        const val GENRE_TABLE_ENTITY = "genre_entity"
    }
}
