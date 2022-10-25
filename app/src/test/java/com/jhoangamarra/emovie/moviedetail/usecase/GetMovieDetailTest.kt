package com.jhoangamarra.emovie.moviedetail.usecase

import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.movie.repository.MovieRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMovieDetailTest {

    private val movieRepository: MovieRepositoryImpl = mockk()
    private lateinit var useCase: GetMovieDetail

    private val movie = Movie(
        id = 10023,
        title = "ToRatedMovieTitle",
        originalTitle = "OriginalTitle",
        language = "EN",
        date = "",
        voteAverage = 6.5F,
        overview = "",
        genres = listOf("genre"),
        backdropPath = "urlBackdrop",
        posterPath = "urlPoster",
        trailerVideo = null
    )

    @Before
    fun setup() {
        useCase = GetMovieDetail(movieRepository)
    }

    @Test
    fun `when invoke then it should return a movie with the detail`() = runTest {

        coEvery {
            movieRepository.getMovieDetail(movieId = movie.id)
        } answers {
            movie
        }
        val useCaseResponse = useCase(movieId = movie.id)

        Assert.assertEquals(movie, useCaseResponse)

        coVerify(exactly = 1) {
            movieRepository.getMovieDetail(movie.id)
        }
    }
}
