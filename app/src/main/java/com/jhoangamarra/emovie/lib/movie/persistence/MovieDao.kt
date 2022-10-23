package com.jhoangamarra.emovie.lib.movie.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.jhoangamarra.emovie.lib.movie.model.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    fun insert(movieEntity: MovieEntity)

    @Insert(onConflict = REPLACE)
    fun insert(movieListEntities : List<MovieEntity>)

    @Query("SELECT * FROM movieentity WHERE type=:type")
    fun getAllByType(type : String) : Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentity WHERE id=:id")
    fun getById(id : Long) : MovieEntity
}