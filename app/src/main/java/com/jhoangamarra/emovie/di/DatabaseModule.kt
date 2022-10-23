package com.jhoangamarra.emovie.di

import android.content.Context
import androidx.room.Room
import com.jhoangamarra.emovie.lib.movie.persistence.EMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): EMovieDatabase =
        Room.databaseBuilder(
            context,
            EMovieDatabase::class.java,
            EMovieDatabase::class.java.name
        ).build()

}