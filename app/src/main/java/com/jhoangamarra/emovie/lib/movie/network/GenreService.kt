package com.jhoangamarra.emovie.lib.movie.network

import com.jhoangamarra.emovie.lib.movie.model.ApiGenreListResponse
import retrofit2.http.GET

interface GenreService {

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(): ApiGenreListResponse
}
