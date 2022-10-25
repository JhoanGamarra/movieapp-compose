package com.jhoangamarra.emovie.lib.movie.repository

import app.cash.turbine.test
import com.jhoangamarra.emovie.lib.movie.model.ApiGenre
import com.jhoangamarra.emovie.lib.movie.model.ApiGenreListResponse
import com.jhoangamarra.emovie.lib.movie.model.ApiMovie
import com.jhoangamarra.emovie.lib.movie.model.ApiMovieListResponse
import com.jhoangamarra.emovie.lib.movie.model.ApiTrailer
import com.jhoangamarra.emovie.lib.movie.model.ApiTrailerListResponse
import com.jhoangamarra.emovie.lib.movie.model.GenreEntity
import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.movie.model.MovieEntity
import com.jhoangamarra.emovie.lib.movie.network.GenreService
import com.jhoangamarra.emovie.lib.movie.network.MovieService
import com.jhoangamarra.emovie.lib.movie.persistence.GenreDao
import com.jhoangamarra.emovie.lib.movie.persistence.MovieDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {

    private val movieService: MovieService = mockk()
    private val genreService: GenreService = mockk()
    private val movieDao: MovieDao = mockk(relaxed = true)
    private val genreDao: GenreDao = mockk(relaxed = true)
    private lateinit var movieRepository: MovieRepositoryImpl
    private val apiGenre = ApiGenre(23, "genreName")
    private val genreList = listOf(apiGenre)
    private val apiGenreListResponse = ApiGenreListResponse(genreList)
    private val apiMovie = ApiMovie(
        id = 10023,
        title = "UpcomingMovieTitle",
        originalTitle = "OriginalTitle",
        language = "EN",
        date = "",
        voteAverage = 6.5F,
        overview = "",
        backdropPath = "urlBackdrop",
        posterPath = "urlPoster",
        genres = arrayListOf(23)
    )
    private val genreEntity = GenreEntity(apiGenre.id, apiMovie.id, apiGenre.name)
    private val upcomingApiMovieList = listOf(apiMovie)
    private val topRatedApiMovieList = listOf(apiMovie.copy(title = "TopRatedMovieTitle"))
    private val topRatedApiMovieResponse = ApiMovieListResponse(topRatedApiMovieList)
    private val upcomingApiMovieListResponse = ApiMovieListResponse(upcomingApiMovieList)
    private val apiTrailer = ApiTrailer("id", "", "123", "YouTube", "Trailer", true)
    private val apiTrailers = listOf(apiTrailer)
    private val apiTrailerListResponse = ApiTrailerListResponse(trailers = apiTrailers)
    private val genresList = listOf("genre1", "genre2")

    private val upcomingMovieEntity = MovieEntity(
        id = 10023,
        title = "UpcomingMovieTitle",
        originalTitle = "OriginalTitle",
        language = "EN",
        date = "",
        voteAverage = 6.5F,
        overview = "",
        backdropPath = "urlBackdrop",
        posterPath = "urlPoster",
        trailerVideo = apiTrailer.key,
        type = UPCOMING_MOVIE_TYPE
    )
    private val upcomingMovieEntityList = listOf(upcomingMovieEntity)
    private val topRatedMovieEntity = MovieEntity(
        id = 10023,
        title = "TopRatedMovieTitle",
        originalTitle = "OriginalTitle",
        language = "EN",
        date = "",
        voteAverage = 6.5F,
        overview = "",
        backdropPath = "urlBackdrop",
        posterPath = "urlPoster",
        trailerVideo = apiTrailer.key,
        type = TOP_RATED_MOVIE_TYPE
    )
    private val topRatedMovieEntityList = listOf(topRatedMovieEntity)
    private val upcomingMovie = Movie(
        id = upcomingMovieEntity.id,
        title = upcomingMovieEntity.title,
        originalTitle = upcomingMovieEntity.originalTitle,
        language = upcomingMovieEntity.language,
        date = upcomingMovieEntity.date,
        voteAverage = upcomingMovieEntity.voteAverage,
        overview = upcomingMovieEntity.overview,
        genres = genresList,
        backdropPath = upcomingMovieEntity.backdropPath,
        posterPath = upcomingMovieEntity.posterPath,
        trailerVideo = upcomingMovieEntity.trailerVideo
    )
    private val topRatedMovie = Movie(
        id = topRatedMovieEntity.id,
        title = topRatedMovieEntity.title,
        originalTitle = topRatedMovieEntity.originalTitle,
        language = topRatedMovieEntity.language,
        date = topRatedMovieEntity.date,
        voteAverage = topRatedMovieEntity.voteAverage,
        overview = topRatedMovieEntity.overview,
        genres = genresList,
        backdropPath = topRatedMovieEntity.backdropPath,
        posterPath = topRatedMovieEntity.posterPath,
        trailerVideo = topRatedMovieEntity.trailerVideo
    )
    private val upcomingMovieEntityFlow = flowOf(listOf(upcomingMovieEntity))
    private val topRatedMovieEntityFlow = flowOf(listOf(topRatedMovieEntity))
    private val upcomingMovieList = listOf(upcomingMovie)
    private val topRatedMovieList = listOf(topRatedMovie)

    @Before
    fun setup() {
        movieRepository = MovieRepositoryImpl(
            movieService = movieService,
            movieDao = movieDao,
            genreDao = genreDao,
            genreService = genreService
        )
    }

    @Test
    fun `when getUpcomingMovies is called then it should return a flow with list of the database movies filter by UPCOMING_MOVIE_TYPE`() = runTest {

        coEvery {
            movieDao.getAllByType(UPCOMING_MOVIE_TYPE)
        } answers {
            upcomingMovieEntityFlow
        }

        coEvery {
            genreDao.getGenreNameByMovieId(upcomingMovieEntity.id)
        } answers {
            genresList
        }

        movieRepository.getUpcomingMovies().test {
            Assert.assertEquals(upcomingMovieList, awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) {
            movieDao.getAllByType(UPCOMING_MOVIE_TYPE)
            genreDao.getGenreNameByMovieId(upcomingMovieEntity.id)
        }
    }

    @Test
    fun `when getTopRatedMovies is called then it should return a flow with list of the database movies filter by TOP_RATED_MOVIE_TYPE`() = runTest {

        coEvery {
            movieDao.getAllByType(TOP_RATED_MOVIE_TYPE)
        } answers {
            topRatedMovieEntityFlow
        }

        coEvery {
            genreDao.getGenreNameByMovieId(topRatedMovieEntity.id)
        } answers {
            genresList
        }

        movieRepository.getTopRatedMovies().test {
            Assert.assertEquals(topRatedMovieList, awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) {
            movieDao.getAllByType(TOP_RATED_MOVIE_TYPE)
            genreDao.getGenreNameByMovieId(topRatedMovieEntity.id)
        }
    }

    @Test
    fun `when fetchUpcomingMovies is called then it should fetch upcoming movies from api ands insert in the database`() = runTest {

        coEvery {
            movieService.getUpcomingMoviesListItems()
        } answers {
            upcomingApiMovieListResponse
        }

        coEvery {
            genreService.getMovieGenreList()
        } answers {
            apiGenreListResponse
        }

        coEvery {
            genreService.getMovieGenreList()
        } answers {
            apiGenreListResponse
        }

        coEvery {
            movieService.getMovieTrailers(apiMovie.id)
        } answers {
            apiTrailerListResponse
        }

        movieRepository.fetchUpcomingMovies()

        coVerify(exactly = 1) {
            genreDao.insert(genreEntity)
            movieDao.insert(upcomingMovieEntityList)
            movieService.getUpcomingMoviesListItems()
            genreService.getMovieGenreList()
            movieService.getMovieTrailers(apiMovie.id)
        }
    }

    @Test
    fun `when fetchTopRatedMovies is called then it should fetch top rated movies from api ands insert in the database`() = runTest {

        coEvery {
            movieService.getTopRatedMoviesListItems()
        } answers {
            topRatedApiMovieResponse
        }

        coEvery {
            genreService.getMovieGenreList()
        } answers {
            apiGenreListResponse
        }

        coEvery {
            genreService.getMovieGenreList()
        } answers {
            apiGenreListResponse
        }

        coEvery {
            movieService.getMovieTrailers(apiMovie.id)
        } answers {
            apiTrailerListResponse
        }

        movieRepository.fetchTopRatedMovies()

        coVerify(exactly = 1) {
            genreDao.insert(genreEntity)
            movieDao.insert(topRatedMovieEntityList)
            movieService.getTopRatedMoviesListItems()
            genreService.getMovieGenreList()
            movieService.getMovieTrailers(apiMovie.id)
        }
    }

    @Test
    fun `given a movieId when getMovieDetail is called then it should return a movie from the database`() = runTest {

        coEvery {
            movieDao.getById(apiMovie.id)
        } answers {
            upcomingMovieEntity
        }
        coEvery {
            genreDao.getGenreNameByMovieId(apiMovie.id)
        } answers {
            genresList
        }

        val movieResponse = movieRepository.getMovieDetail(apiMovie.id)

        Assert.assertEquals(upcomingMovie, movieResponse)

        coVerify {
            movieDao.getById(apiMovie.id)
            genreDao.getGenreNameByMovieId(apiMovie.id)
        }
    }
}

private const val UPCOMING_MOVIE_TYPE = "UPCOMING"
private const val TOP_RATED_MOVIE_TYPE = "TOP_RATED"
