package com.jhoangamarra.emovie.moviedetail.model

data class MovieDetail(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val language: String,
    val date: String,
    val voteAverage: Float,
    val overview: String,
    val genres: List<String>,
    val backdropPath: String,
    val posterPath: String,
)
