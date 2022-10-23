package com.jhoangamarra.emovie.home.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jhoangamarra.emovie.home.ui.HomeViewModel
import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.fresco.FrescoImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoviePosterCard(viewModel: HomeViewModel,  movie: Movie) {
    AnimatedContent(
        targetState = rememberInfiniteTransition(),
        transitionSpec = {
            fadeIn(animationSpec = tween(220, delayMillis = 50)) +
                    scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 50)) with
                    fadeOut(animationSpec = tween(90))
        }
    ) {
        FrescoImage(
            imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(220.dp)
                .width(150.dp)
                .clickable {
                    viewModel.onItemClicked(movie.id)
                },
            observeLoadingProcess = true,
            circularReveal = CircularReveal()
        )
    }
}