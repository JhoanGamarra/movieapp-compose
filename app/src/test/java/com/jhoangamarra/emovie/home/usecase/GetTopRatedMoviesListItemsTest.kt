package com.jhoangamarra.emovie.home.usecase

import app.cash.turbine.test
import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.movie.repository.MovieRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetTopRatedMoviesListItemsTest {

    private val movieRepository: MovieRepositoryImpl = mockk()
    private lateinit var useCase: GetTopRatedMoviesListItems
    private val movies = listOf(
        Movie(
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
    )

    @Before
    fun setup() {
        useCase = GetTopRatedMoviesListItems(movieRepository = movieRepository)
    }

    @Test
    fun `when invoke then it should return a flow with the top rated movies list`() = runTest {

        coEvery {
            movieRepository.getTopRatedMovies()
        } answers {
            flowOf(movies)
        }

        useCase().test {
            Assert.assertEquals(
                movies,
                awaitItem()
            )
            awaitComplete()
        }

        coVerify(exactly = 1) {
            movieRepository.getTopRatedMovies()
        }
    }
}
