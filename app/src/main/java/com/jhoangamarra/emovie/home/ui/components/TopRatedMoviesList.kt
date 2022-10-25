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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoangamarra.emovie.R
import com.jhoangamarra.emovie.lib.movie.model.Movie

@Composable
fun TopRatedMoviesList(onItemClicked: (Long) -> Unit, movies: List<Movie>) {
    Text(
        modifier = Modifier.padding(top = 20.dp),
        text = stringResource(id = R.string.label_top_rated_movies),
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
                { id -> onItemClicked(id) },
                movie = movie
            )
        }
    }
}
