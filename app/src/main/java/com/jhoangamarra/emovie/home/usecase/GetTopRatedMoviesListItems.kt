package com.jhoangamarra.emovie.home.usecase

import com.jhoangamarra.emovie.lib.movie.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesListItems @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke() = movieRepository.getTopRatedMovies()

}