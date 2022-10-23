package com.jhoangamarra.emovie.lib.movie.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.jhoangamarra.emovie.lib.movie.model.GenreEntity

@Dao
interface GenreDao {

    @Insert(onConflict = IGNORE)
    fun insert(genreListEntities : List<GenreEntity>)

    @Insert(onConflict = IGNORE)
    fun insert(genreEntity : GenreEntity)

    @Query("SELECT name FROM genreentity WHERE movieId=:movieId")
    fun getGenreNameByMovieId(movieId : Long) : List<String>

}