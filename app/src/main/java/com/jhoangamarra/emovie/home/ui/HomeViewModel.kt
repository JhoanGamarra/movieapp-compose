package com.jhoangamarra.emovie.home.ui

import com.jhoangamarra.emovie.home.usecase.FetchTopRatedMovies
import com.jhoangamarra.emovie.home.usecase.FetchUpcomingMovies
import com.jhoangamarra.emovie.home.usecase.GetTopRatedMoviesListItems
import com.jhoangamarra.emovie.home.usecase.GetUpcomingMoviesListItems
import com.jhoangamarra.emovie.lib.definition.AbstractViewModel
import com.jhoangamarra.emovie.lib.definition.CoroutineContextProvider
import com.jhoangamarra.emovie.lib.definition.launch
import com.jhoangamarra.emovie.lib.movie.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUpcomingMovies: FetchUpcomingMovies,
    private val getUpcomingMoviesListItems: GetUpcomingMoviesListItems,
    private val fetchTopRatedMovies: FetchTopRatedMovies,
    private val getTopRatedMoviesListItems: GetTopRatedMoviesListItems,
    coroutineContextProvider: CoroutineContextProvider
) : AbstractViewModel<HomeViewModel.State, HomeViewModel.Event>(
    initialState = State(),
    coroutineContextProvider = coroutineContextProvider
) {

    init {
        getUpcomingMovies()
        getTopRatedMovies()
        getFilterOptions()
    }

    fun filterMovies(option: String, filter: Boolean) {

        val topRatedMovies = currentState().topRatedMovieListItems
        val filteredMovies = if (filter) {
            topRatedMovies?.let {
                it.filter { movie ->
                    (movie.language == option || movie.date.contains(option)) && filter
                }
            }
        } else {
            topRatedMovies
        }
        launch {
            update { it.copy(filteredMoviesListItems = filteredMovies) }
        }
    }

    fun onItemClicked(movieId: Long) {
        _event.tryEmit(Event.NavigateToDetail(id = movieId))
    }

    private fun fetchUpcomingMovieListItems() {
        launch {
            update { it.copy(loading = true) }
            withContext(io) {
                fetchUpcomingMovies()
            }
            update {
                it.copy(loading = false)
            }
        }
    }

    private fun fetchTopRatedMovieListItems() {
        launch {
            update { it.copy(loading = true) }
            withContext(io) {
                fetchTopRatedMovies()
            }
            update {
                it.copy(loading = false)
            }
        }
    }

    private fun getTopRatedMovies() {
        launch {
            getTopRatedMoviesListItems()
                .flowOn(io)
                .collect { movieList ->
                    if (movieList.isEmpty()) {
                        fetchTopRatedMovieListItems()
                    } else {
                        update {
                            it.copy(
                                topRatedMovieListItems = movieList,
                                filteredMoviesListItems = movieList,
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    private fun getUpcomingMovies() {
        launch {
            getUpcomingMoviesListItems()
                .flowOn(io)
                .collect { moviesList ->
                    if (moviesList.isEmpty()) {
                        fetchUpcomingMovieListItems()
                    } else {
                        update {
                            it.copy(
                                upcomingMovieListItems = moviesList,
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    private fun getFilterOptions() {
        launch {
            update {
                it.copy(
                    filterOptionsList = listOf(
                        FilterOption(key = "en", value = "En Ingles", selected = true),
                        FilterOption(key = "2020", value = "Lanzados en 2020", selected = false)
                    )
                )
            }
        }
    }

    data class State(
        val loading: Boolean = true,
        val upcomingMovieListItems: List<Movie>? = null,
        val topRatedMovieListItems: List<Movie>? = null,
        val filteredMoviesListItems: List<Movie>? = null,
        val filterOptionsList: List<FilterOption>? = null
    )

    sealed interface Event {
        data class NavigateToDetail(val id: Long) : Event
    }

    data class FilterOption(val key: String, val value: String, val selected: Boolean)
}
