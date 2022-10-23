package com.jhoangamarra.emovie.lib.movie.repository

import com.jhoangamarra.emovie.lib.movie.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getUpcomingMovies(): Flow<List<Movie>>

    fun getTopRatedMovies(): Flow<List<Movie>>

    suspend fun fetchUpcomingMovies()

    suspend fun fetchTopRatedMovies()

    suspend fun getMovieDetail(movieId: Long): Movie

}