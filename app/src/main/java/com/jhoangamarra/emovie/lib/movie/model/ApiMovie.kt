package com.jhoangamarra.emovie.lib.movie.model

import com.google.gson.annotations.SerializedName

data class ApiMovie(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("original_language")
    val language: String,
    @SerializedName("release_date")
    val date: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genre_ids")
    val genres: ArrayList<Int>,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String
) {

    fun toMovieEntity(type: String, trailerMovie : String): MovieEntity =
        MovieEntity(
            id = id,
            title = title,
            originalTitle = originalTitle,
            language = language,
            date = date,
            voteAverage = voteAverage,
            overview = overview,
            backdropPath = backdropPath,
            posterPath = posterPath,
            type = type,
            trailerVideo = trailerMovie
        )

}