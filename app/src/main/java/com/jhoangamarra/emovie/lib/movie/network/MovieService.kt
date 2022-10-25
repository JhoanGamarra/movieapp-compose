package com.jhoangamarra.emovie.lib.movie.network

import com.jhoangamarra.emovie.lib.movie.model.ApiMovie
import com.jhoangamarra.emovie.lib.movie.model.ApiMovieListResponse
import com.jhoangamarra.emovie.lib.movie.model.ApiTrailerListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Long): ApiMovie

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesListItems(): ApiMovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesListItems(): ApiMovieListResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(@Path("movie_id") movieId: Long): ApiTrailerListResponse
}
