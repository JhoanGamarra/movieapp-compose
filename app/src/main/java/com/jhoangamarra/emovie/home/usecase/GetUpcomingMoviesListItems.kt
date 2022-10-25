package com.jhoangamarra.emovie.home.usecase

import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.movie.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetUpcomingMoviesListItems @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<List<Movie>> = movieRepository.getUpcomingMovies()
}
