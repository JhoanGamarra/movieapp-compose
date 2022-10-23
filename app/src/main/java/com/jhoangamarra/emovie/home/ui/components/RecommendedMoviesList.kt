package com.jhoangamarra.emovie.home.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jhoangamarra.emovie.home.ui.HomeViewModel
import com.jhoangamarra.emovie.lib.movie.model.Movie

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendedMoviesList(viewModel : HomeViewModel, movies: List<Movie>) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(130.dp),
        modifier = Modifier
            .size(width = 800.dp, height = 650.dp)
            .padding(top = 20.dp, end = 20.dp, bottom = 10.dp)
    ) {
        items(items = movies) { movie ->
            Row(modifier = Modifier.padding(10.dp)) {
                MoviePosterCard(
                    viewModel = viewModel,
                    movie = movie
                )
            }
        }
    }
}