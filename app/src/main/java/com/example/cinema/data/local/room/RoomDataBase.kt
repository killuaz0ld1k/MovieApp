package com.example.cinema.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.data.local.room.dao.MoviesDao
import com.example.cinema.data.local.room.entities.ActorEntity
import com.example.cinema.data.local.room.entities.GenreEntity
import com.example.cinema.data.local.room.entities.MovieDetailsEntity
import com.example.cinema.data.local.room.entities.MovieEntity

@Database(entities = [GenreEntity::class,MovieEntity::class,ActorEntity::class,MovieDetailsEntity::class], version = 11)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun moviesDao() : MoviesDao
}