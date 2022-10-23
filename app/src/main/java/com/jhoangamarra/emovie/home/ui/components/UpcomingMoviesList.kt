package com.jhoangamarra.emovie.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoangamarra.emovie.home.ui.HomeViewModel
import com.jhoangamarra.emovie.lib.movie.model.Movie

@Composable
fun UpcomingMoviesList(viewModel : HomeViewModel, movies: List<Movie>) {
    Text(
        modifier = Modifier.padding(top = 20.dp),
        text = " Proximos estrenos",
        textAlign = TextAlign.Start,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
    LazyRow(
        Modifier
            .height(250.dp)
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ) {
        items(items = movies) { movie ->
            MoviePosterCard(
                viewModel = viewModel,
                movie = movie
            )
        }
    }
}