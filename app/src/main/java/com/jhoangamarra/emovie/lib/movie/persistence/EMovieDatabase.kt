package com.jhoangamarra.emovie.lib.movie.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jhoangamarra.emovie.lib.movie.model.GenreEntity
import com.jhoangamarra.emovie.lib.movie.model.MovieEntity


@Database(entities = [MovieEntity::class, GenreEntity::class], version = 1)
abstract class EMovieDatabase : RoomDatabase(){

    abstract fun movieDao() : MovieDao

    abstract fun genreDao() : GenreDao

}