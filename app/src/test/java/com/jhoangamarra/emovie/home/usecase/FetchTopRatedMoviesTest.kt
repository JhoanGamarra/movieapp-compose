package com.jhoangamarra.emovie.home.usecase

import com.jhoangamarra.emovie.lib.movie.repository.MovieRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class FetchTopRatedMoviesTest {


    private val movieRepository : MovieRepositoryImpl = mockk()
    private lateinit var useCase: FetchTopRatedMovies

    @Before
    fun setup(){
        useCase = FetchTopRatedMovies(movieRepository)
    }

    @Test
    fun `when invoke then it should call the fetchTopRatedMovies fun of the repository`()= runTest {

        coEvery {
            movieRepository.fetchTopRatedMovies()
        }answers {}

        useCase()
        coVerify(exactly = 1) {
            movieRepository.fetchTopRatedMovies()
        }

    }

}