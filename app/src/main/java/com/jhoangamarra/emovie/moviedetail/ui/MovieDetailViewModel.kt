package com.jhoangamarra.emovie.moviedetail.ui

import com.jhoangamarra.emovie.lib.definition.AbstractViewModel
import com.jhoangamarra.emovie.lib.definition.CoroutineContextProvider
import com.jhoangamarra.emovie.lib.definition.launch
import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.moviedetail.model.MovieDetail
import com.jhoangamarra.emovie.moviedetail.usecase.GetMovieDetail
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext


class MovieDetailViewModel @AssistedInject constructor(
    @Assisted private val id: Long,
    private val getMovieDetail: GetMovieDetail,
    coroutineContextProvider: CoroutineContextProvider
) : AbstractViewModel<MovieDetailViewModel.State, MovieDetailViewModel.Event>(
    initialState = State(),
    coroutineContextProvider = coroutineContextProvider
) {


    init {
        getMovieDetail()
    }

    private fun getMovieDetail(){
        launch {
            update { it.copy(loading = true) }
            val movieDetail = withContext(io){
                getMovieDetail(id)
            }
            update { it.copy(movieDetail = movieDetail, loading = false) }
        }

    }

    data class State(
        val loading: Boolean = false,
        val movieDetail: Movie? = null,
    )

    sealed interface Event


    @AssistedFactory
    interface Factory {
        fun create(id: Long): MovieDetailViewModel
    }

}