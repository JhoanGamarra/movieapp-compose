package com.jhoangamarra.emovie.home.usecase

import com.jhoangamarra.emovie.lib.movie.repository.MovieRepository
import javax.inject.Inject

class FetchTopRatedMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke() = movieRepository.fetchTopRatedMovies()
}