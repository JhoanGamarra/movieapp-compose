package com.jhoangamarra.emovie.home.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jhoangamarra.emovie.lib.movie.model.Movie

@Composable
fun RecommendedMoviesList(onItemClicked: (Long) -> Unit, movies: List<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(130.dp),
        modifier = Modifier
            .size(width = 800.dp, height = 650.dp)
            .padding(top = 20.dp, end = 20.dp, bottom = 10.dp)
    ) {
        items(items = movies) { movie ->
            Row(modifier = Modifier.padding(10.dp)) {
                MoviePosterCard(
                    onItemClicked,
                    movie = movie
                )
            }
        }
    }
}
