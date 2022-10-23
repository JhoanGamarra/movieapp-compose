package com.jhoangamarra.emovie.lib.movie.model

import com.google.gson.annotations.SerializedName

data class ApiMovieListResponse(
    @SerializedName("results")
    val movies: List<ApiMovie>
)