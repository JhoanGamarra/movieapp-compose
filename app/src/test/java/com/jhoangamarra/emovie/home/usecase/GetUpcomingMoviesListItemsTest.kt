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


class GetUpcomingMoviesListItemsTest {

    private val movieRepository: MovieRepositoryImpl = mockk()
    private lateinit var useCase: GetUpcomingMoviesListItems
    private val movies = listOf(
        Movie(
            id = 10023,
            title = "UpcomingMovieTitle",
            originalTitle = "OriginalTitle",
            language = "EN",
            date = "",
            voteAverage = 6.5F,
            overview = "",
            genres = listOf("genre"),
            backdropPath = "urlBackdrop",
            posterPath = "urlPoster",
            trailerVideo = "trailerURL"
        )
    )

    @Before
    fun setup() {
        useCase = GetUpcomingMoviesListItems(movieRepository = movieRepository)
    }

    @Test
    fun `when invoke then it should return a flow with the upcoming movies list`() = runTest {

        coEvery {
            movieRepository.getUpcomingMovies()
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
            movieRepository.getUpcomingMovies()
        }

    }
}