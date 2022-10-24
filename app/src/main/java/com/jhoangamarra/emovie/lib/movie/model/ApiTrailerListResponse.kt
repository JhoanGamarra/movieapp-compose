package com.jhoangamarra.emovie.lib.movie.model

import com.google.gson.annotations.SerializedName

data class ApiTrailerListResponse(
    @SerializedName("results")
    val trailers: List<ApiTrailer>
)
