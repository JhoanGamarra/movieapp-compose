package com.jhoangamarra.emovie.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.jhoangamarra.emovie.LocalNavControllerProvider
import com.jhoangamarra.emovie.R
import com.jhoangamarra.emovie.home.navigation.HomeRoute
import com.jhoangamarra.emovie.home.ui.components.FilterOptionCard
import com.jhoangamarra.emovie.home.ui.components.RecommendedMoviesList
import com.jhoangamarra.emovie.home.ui.components.TopRatedMoviesList
import com.jhoangamarra.emovie.home.ui.components.UpcomingMoviesList
import com.jhoangamarra.emovie.lib.navigation.composable
import com.jhoangamarra.emovie.moviedetail.navigation.MovieDetailRoute

fun NavGraphBuilder.homeScreen() = composable(route = HomeRoute) {

    val viewModel: HomeViewModel = hiltViewModel(it)
    val state: HomeViewModel.State by viewModel.state.collectAsState(HomeViewModel.State())
    val events: HomeViewModel.Event? by viewModel.events.collectAsState(initial = null)
    HomeScreen(
        state,
        events,
        onItemClicked = { movieId ->
            viewModel.onItemClicked(movieId)
        },
        onFilterMovies = { option, filter ->
            viewModel.filterMovies(option, filter)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeViewModel.State,
    events: HomeViewModel.Event? = null,
    onItemClicked: (Long) -> Unit,
    onFilterMovies: (String, Boolean) -> Unit
) {

    val navController = LocalNavControllerProvider.current
    val upcomingMovies = state.upcomingMovieListItems ?: emptyList()
    val topRatedMovies = state.topRatedMovieListItems ?: emptyList()
    val recommendedMovies = state.filteredMoviesListItems ?: emptyList()
    val filterOptions = state.filterOptionsList ?: emptyList()

    LaunchedEffect(key1 = events) {
        val event = events ?: return@LaunchedEffect

        if (event !is HomeViewModel.Event.NavigateToDetail) {
            return@LaunchedEffect
        }
        MovieDetailRoute.navigate(event.id, navController)
    }

    LazyColumn(
        Modifier
            .padding(start = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Column {
                StickyTitle()
                UpcomingMoviesList(
                    onItemClicked = { id -> onItemClicked(id) },
                    movies = upcomingMovies
                )
                TopRatedMoviesList(
                    onItemClicked = { id -> onItemClicked(id) },
                    movies = topRatedMovies
                )
                RecommendedMoviesTitle(
                    onFilterMovies = { key, selected -> onFilterMovies(key, selected) },
                    filterOptions = filterOptions
                )
            }
        }
        item {
            RecommendedMoviesList(onItemClicked = { id -> onItemClicked(id) }, recommendedMovies)
        }
    }
}

@Composable
fun StickyTitle() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Row(Modifier.align(Alignment.Center)) {
            Text(
                textAlign = TextAlign.Center,
                text = "eMovie",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun RecommendedMoviesTitle(
    onFilterMovies: (String, Boolean) -> Unit,
    filterOptions: List<HomeViewModel.FilterOption>
) {
    Text(
        modifier = Modifier.padding(top = 20.dp),
        text = stringResource(id = R.string.label_recommended_movies),
        textAlign = TextAlign.Start,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(start = 10.dp, top = 20.dp)
    ) {
        items(items = filterOptions) { option ->
            FilterOptionCard(onFilterMovies, option)
        }
    }
}
