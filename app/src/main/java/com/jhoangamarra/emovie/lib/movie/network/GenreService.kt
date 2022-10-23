package com.jhoangamarra.emovie.lib.movie.network

import com.jhoangamarra.emovie.lib.movie.model.ApiGenreListResponse
import com.jhoangamarra.emovie.lib.movie.model.ApiMovie
import retrofit2.http.GET
import retrofit2.http.Path

interface GenreService {


    @GET("genre/movie/list")
    suspend fun getMovieGenreList(): ApiGenreListResponse

}