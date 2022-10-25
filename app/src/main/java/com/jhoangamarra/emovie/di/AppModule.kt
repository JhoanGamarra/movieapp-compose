package com.jhoangamarra.emovie.di

import com.jhoangamarra.emovie.lib.movie.persistence.EMovieDatabase
import com.jhoangamarra.emovie.lib.movie.persistence.GenreDao
import com.jhoangamarra.emovie.lib.movie.persistence.MovieDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Reusable
    @Provides
    fun provideMovieDao(eMovieDatabase: EMovieDatabase): MovieDao = eMovieDatabase.movieDao()

    @Reusable
    @Provides
    fun provideGenreDao(eMovieDatabase: EMovieDatabase): GenreDao = eMovieDatabase.genreDao()
}
