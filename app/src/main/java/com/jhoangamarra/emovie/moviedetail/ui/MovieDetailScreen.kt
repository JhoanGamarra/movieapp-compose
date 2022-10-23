package com.jhoangamarra.emovie.moviedetail.ui

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jhoangamarra.emovie.LocalNavControllerProvider
import com.jhoangamarra.emovie.R
import com.jhoangamarra.emovie.lib.LocalViewModelStore
import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.navigation.composable
import com.jhoangamarra.emovie.lib.utils.mirroringBackIcon
import com.jhoangamarra.emovie.moviedetail.navigation.MovieDetailRoute
import com.jhoangamarra.emovie.ui.theme.EMovieTheme
import com.jhoangamarra.emovie.ui.theme.Neutral0
import com.jhoangamarra.emovie.ui.theme.Neutral1
import com.jhoangamarra.emovie.ui.theme.Neutral3
import com.jhoangamarra.emovie.ui.theme.Neutral8
import com.jhoangamarra.emovie.ui.theme.Shadow1
import com.jhoangamarra.emovie.ui.theme.tornado1
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.fresco.FrescoImage
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import kotlin.math.max
import kotlin.math.min

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)
private const val DividerAlpha = 0.12f

fun NavGraphBuilder.movieDetailScreen() = composable(
    route = MovieDetailRoute,
    arguments = listOf(navArgument(MovieDetailRoute.IdArgument) { type = NavType.LongType })
) { navBackStackEntry ->

    val id = navBackStackEntry.arguments!!.getLong(MovieDetailRoute.IdArgument)
    val viewModelStore = LocalViewModelStore.current
    val activity = LocalContext.current as Activity

    val viewModel: MovieDetailViewModel = viewModelStore.get("${MovieDetailKey}_$id") {
        getViewModel(id, activity)
    } as MovieDetailViewModel

    val state by viewModel.state.collectAsState(initial = MovieDetailViewModel.State())

    MovieDetailScreen(state = state, modifier = Modifier.verticalScroll(rememberScrollState()))


}

@Composable
fun MovieDetailScreen(state: MovieDetailViewModel.State, modifier: Modifier) {

    val movie = state.movieDetail ?: return
    val navController = LocalNavControllerProvider.current

    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(scroll)
        Title(movie) { scroll.value }
        Image(movie.posterPath) { scroll.value }
        Up { navController.navigateUp() }
    }

//    Scaffold(topBar = {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_back),
//                contentDescription = "back",
//                tint = MaterialTheme.colors.onSurface.copy(alpha = .5f),
//                modifier = Modifier
//                    .size(30.dp)
//                    .weight(.1f)
//                    .clickable {
//                        navController.popBackStack()
//                    }
//            )
//        }
//    }) {
//
//
//    }

}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(tornado1))
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = Neutral3,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
private fun Body(
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                //.statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Surface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.movie_plot),
                        style = MaterialTheme.typography.overline,
                        color = Color.White,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(16.dp))
                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        text = "Movie plot text",
                        style = MaterialTheme.typography.body1,
                        color = Color.White,
                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )
                    val textButton = if (seeMore) {
                        stringResource(id = R.string.see_more)
                    } else {
                        stringResource(id = R.string.see_less)
                    }
                    Text(
                        text = textButton,
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .heightIn(20.dp)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clickable {
                                seeMore = !seeMore
                            }
                    )
                    Spacer(Modifier.height(40.dp))

                }
            }
        }
    }
}

@Composable
private fun Title(movie: Movie, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            //.statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Neutral8)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h4,
            color = Neutral0,
            modifier = HzPadding
        )
        Text(
            text = movie.date,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            color = Neutral1,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = movie.language,
            style = MaterialTheme.typography.h6,
            color = Shadow1,
            modifier = HzPadding
        )

        Spacer(Modifier.height(8.dp))
        EMovieDivider()
    }
}

@Composable
fun EMovieDivider(
    modifier: Modifier = Modifier,
    color: Color = Neutral3.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
        startIndent = startIndent
    )
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding//.then(Modifier.statusBarsPadding())
    ) {
        FrescoImage(
            imageUrl = "https://image.tmdb.org/t/p/w500${imageUrl}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(220.dp)
                .width(150.dp),
            observeLoadingProcess = true,
            circularReveal = CircularReveal()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}


private fun getViewModel(
    id: Long,
    activity: Activity
): MovieDetailViewModel = EntryPointAccessors.fromActivity(
    activity = activity,
    entryPoint = MovieDetailEntryPoint::class.java
).movieDetailFactory().create(id)

@EntryPoint
@InstallIn(ActivityComponent::class)
internal interface MovieDetailEntryPoint {

    fun movieDetailFactory(): MovieDetailViewModel.Factory
}

private const val MovieDetailKey = "MovieDetailKey"