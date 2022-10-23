package com.jhoangamarra.emovie.lib.movie.repository

import android.util.Log
import com.jhoangamarra.emovie.lib.movie.model.ApiGenre
import com.jhoangamarra.emovie.lib.movie.model.GenreEntity
import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.movie.network.GenreService
import com.jhoangamarra.emovie.lib.movie.network.MovieService
import com.jhoangamarra.emovie.lib.movie.persistence.GenreDao
import com.jhoangamarra.emovie.lib.movie.persistence.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val genreService: GenreService
) : MovieRepository {

    override fun getUpcomingMovies(): Flow<List<Movie>> {
        return movieDao.getAllByType(UPCOMING_MOVIE_TYPE).map { moviesEntities ->
            moviesEntities.map { movieEntity ->
                val genresNameList = genreDao.getGenreNameByMovieId(movieEntity.id)
                movieEntity.toMovie(genresNameList)
            }
        }
    }

    override fun getTopRatedMovies(): Flow<List<Movie>> {
        return movieDao.getAllByType(TOP_RATED_MOVIE_TYPE).map { moviesEntities ->
            moviesEntities.map { movieEntity ->
                val genresNameList = genreDao.getGenreNameByMovieId(movieEntity.id)
                movieEntity.toMovie(genresNameList)
            }
        }
    }

    override suspend fun fetchUpcomingMovies() {
        val apiMovies = movieService.getUpcomingMoviesListItems().movies
        val entityMovies = apiMovies.map { apiMovie ->
            apiMovie.genres.forEach { genreId ->
                val genre = fetchGenres().first { apiGenre -> apiGenre.id == genreId }
                genreDao.insert(GenreEntity(genreId, apiMovie.id, genre.name))
            }
            apiMovie.toMovieEntity(UPCOMING_MOVIE_TYPE, "getVideoId(movieId = apiMovie.id)")
        }
        movieDao.insert(entityMovies)

    }

    private suspend fun getVideoId(movieId: Long): String {
        return movieService.getMovieTrailers(movieId).trailers.first {
            it.official && (it.type == "Trailer") && (it.site == "YouTube")
        }.key
    }

    private suspend fun fetchGenres(): List<ApiGenre> = genreService.getMovieGenreList().genres


    override suspend fun fetchTopRatedMovies() {
        val apiMovies = movieService.getTopRatedMoviesListItems().movies
        val apiGenres = genreService.getMovieGenreList().genres
        val entityMovies = apiMovies.map { apiMovie ->
            apiMovie.genres.forEach { genreId ->
                val genre = apiGenres.first { apiGenre -> apiGenre.id == genreId }
                genreDao.insert(GenreEntity(genreId, apiMovie.id, genre.name))
            }
            apiMovie.toMovieEntity(TOP_RATED_MOVIE_TYPE, "getVideoId(apiMovie.id)")
        }
        movieDao.insert(entityMovies)

    }

    override suspend fun getMovieDetail(movieId: Long): Movie {
        val movieEntity = movieDao.getById(movieId)
        val genres = genreDao.getGenreNameByMovieId(movieId)
        return movieEntity.toMovie(genres)
    }

}

private const val UPCOMING_MOVIE_TYPE = "UPCOMING"
private const val TOP_RATED_MOVIE_TYPE = "TOP_RATED"