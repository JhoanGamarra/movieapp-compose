package com.jhoangamarra.emovie.lib.movie.model

data class ApiGenre(val id: Int, val name: String) {

    fun toGenreEntity(movieId : Long ) = GenreEntity(
        genreId = id,
        movieId = movieId,
        name = name
    )

}
