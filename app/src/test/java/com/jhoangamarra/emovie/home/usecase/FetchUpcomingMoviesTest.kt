package com.jhoangamarra.emovie.home.usecase

import com.jhoangamarra.emovie.lib.movie.repository.MovieRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class FetchUpcomingMoviesTest {


    private val movieRepository = mockk<MovieRepositoryImpl>()
    private lateinit var useCase : FetchUpcomingMovies

    @Before
    fun setup(){
        useCase = FetchUpcomingMovies(movieRepository)
    }

    @Test
    fun `when invoke then it should call the fetchUpcomingMovies fun of the repository`() = runTest {

        coEvery { movieRepository.fetchUpcomingMovies() } answers {}

        useCase()

        coVerify(exactly = 1) {
            movieRepository.fetchUpcomingMovies()
        }

    }


}