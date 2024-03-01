package com.example.cinema.di.hilt

import android.content.Context
import androidx.room.Room
import com.example.cinema.data.local.room.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            RoomDataBase::class.java, "movieDb"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
}