package com.jhoangamarra.emovie.moviedetail.usecase

import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.movie.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieId: Long): Movie = movieRepository.getMovieDetail(movieId)
}
