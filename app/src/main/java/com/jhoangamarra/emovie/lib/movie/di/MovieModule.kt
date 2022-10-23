package com.jhoangamarra.emovie.lib.movie.di

import com.jhoangamarra.emovie.lib.movie.network.GenreService
import com.jhoangamarra.emovie.lib.movie.network.MovieService
import com.jhoangamarra.emovie.lib.movie.persistence.GenreDao
import com.jhoangamarra.emovie.lib.movie.persistence.MovieDao
import com.jhoangamarra.emovie.lib.movie.repository.MovieRepository
import com.jhoangamarra.emovie.lib.movie.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Reusable
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    @Reusable
    @Provides
    fun provideGenreService(retrofit: Retrofit): GenreService = retrofit.create(GenreService::class.java)

    @Reusable
    @Provides
    fun provideMovieRepository(
        movieService: MovieService,
        movieDao: MovieDao,
        genreDao: GenreDao,
        genreService: GenreService
    ): MovieRepository =
        MovieRepositoryImpl(
            movieService = movieService,
            movieDao = movieDao,
            genreDao = genreDao,
            genreService = genreService
        )
}