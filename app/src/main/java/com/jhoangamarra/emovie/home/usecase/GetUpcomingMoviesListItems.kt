package com.jhoangamarra.emovie.home.usecase

import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.movie.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesListItems @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<List<Movie>> = movieRepository.getUpcomingMovies()

}