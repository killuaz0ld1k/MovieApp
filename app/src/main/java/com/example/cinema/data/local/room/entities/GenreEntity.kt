package com.example.cinema.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.cinema.data.local.room.entities.GenreEntity.Companion.GENRE_TABLE_ENTITY

@Entity(
    tableName = GENRE_TABLE_ENTITY,
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class GenreEntity(
    val id: Int,
    val name: String,
    val parentId : Int
) {
    companion object {
        const val GENRE_TABLE_ENTITY = "genre_entity"
    }
}
