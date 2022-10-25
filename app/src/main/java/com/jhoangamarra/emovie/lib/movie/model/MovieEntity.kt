package com.jhoangamarra.emovie.lib.movie.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "original_language")
    val language: String,
    @ColumnInfo(name = "release_date")
    val date: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "trailer")
    val trailerVideo: String?
) {

    fun toMovie(genres: List<String>): Movie = Movie(
        id = id,
        title = title,
        originalTitle = originalTitle,
        language = language,
        date = date.toYear(),
        voteAverage = voteAverage,
        overview = overview,
        genres = genres,
        backdropPath = backdropPath,
        posterPath = posterPath,
        trailerVideo = trailerVideo
    )

    private fun String.toYear(): String {
        return take(4)
    }
}
