package com.jhoangamarra.emovie.lib.movie.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "genreId"])
data class GenreEntity(
    @ColumnInfo(name = "genreId")
    val genreId: Int,
    @ColumnInfo(name = "movieId")
    val movieId: Long,
    @ColumnInfo(name = "name")
    val name: String
)
