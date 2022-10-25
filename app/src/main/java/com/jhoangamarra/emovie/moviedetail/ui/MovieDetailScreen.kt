package com.jhoangamarra.emovie.moviedetail.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jhoangamarra.emovie.LocalNavControllerProvider
import com.jhoangamarra.emovie.R
import com.jhoangamarra.emovie.lib.LocalViewModelStore
import com.jhoangamarra.emovie.lib.movie.model.Movie
import com.jhoangamarra.emovie.lib.navigation.composable
import com.jhoangamarra.emovie.lib.ui.theme.Neutral0
import com.jhoangamarra.emovie.lib.ui.theme.Neutral1
import com.jhoangamarra.emovie.lib.ui.theme.Neutral3
import com.jhoangamarra.emovie.lib.ui.theme.Neutral4
import com.jhoangamarra.emovie.lib.ui.theme.Neutral8
import com.jhoangamarra.emovie.lib.ui.theme.tornado1
import com.jhoangamarra.emovie.lib.utils.mirroringBackIcon
import com.jhoangamarra.emovie.moviedetail.navigation.MovieDetailRoute
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.fresco.FrescoImage
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

private val TitleHeight = 128.dp
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
    val events by viewModel.events.collectAsState(initial = null)
    MovieDetailScreen(viewModel, state = state, events = events)
}

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    state: MovieDetailViewModel.State,
    events: MovieDetailViewModel.Event? = null
) {

    val movie = state.movieDetail ?: return
    val navController = LocalNavControllerProvider.current
    val context = LocalContext.current

    LaunchedEffect(key1 = events) {
        val event = events ?: return@LaunchedEffect

        if (event !is MovieDetailViewModel.Event.NavigateToTrailerVideo) {
            return@LaunchedEffect
        }
        openYoutubeLink(event.trailerKey, context)
    }

    val toolbarState = rememberCollapsingToolbarScaffoldState()

    Box {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = toolbarState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbarModifier = Modifier.background(Brush.horizontalGradient(tornado1)),
            toolbar = {
                Toolbar(
                    movie = movie,
                    collapsingToolbarState = toolbarState.toolbarState,
                    modifier = Modifier.parallax(0.5f)
                )
            }
        ) {
            val scroll = rememberScrollState(0)
            Column(Modifier.verticalScroll(scroll).fillMaxSize(), horizontalAlignment = CenterHorizontally) {
                Title(movie = movie) {
                    viewModel.openYoutubeTrailer(movie.trailerVideo)
                }
                Spacer(Modifier.height(26.dp))
                Body(movieDetail = movie)
            }
        }
        Up { navController.navigateUp() }
    }
}

@Composable
private fun Toolbar(
    movie: Movie,
    collapsingToolbarState: CollapsingToolbarState,
    modifier: Modifier
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    )
    FrescoImage(
        imageUrl = "$BASE_IMAGE_URL${movie.posterPath}",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(500.dp)
            .graphicsLayer {
                alpha = collapsingToolbarState.progress
            },
        observeLoadingProcess = true,
        circularReveal = CircularReveal()
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .size(36.dp)
            .background(
                color = Neutral4.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = Neutral1,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
private fun Title(movie: Movie, onClickButton: () -> Unit) {

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .background(color = Neutral8)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h5,
            color = Neutral0,
            modifier = HzPadding
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OptionCard(text = movie.date, backgroundColor = Color.White)
            OptionCard(text = movie.language, backgroundColor = Color.White)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = stringResource(id = R.string.ic_start_content_description), tint = Color.White)
                OptionCard(text = movie.voteAverage.toString(), backgroundColor = Color.Yellow)
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyRow(
            modifier = HzPadding,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = movie.genres) { genre ->
                OptionCard(text = genre, backgroundColor = Color.DarkGray, textColor = Color.White)
            }
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally)
                .padding(start = 30.dp, end = 30.dp),
            contentPadding = PaddingValues(vertical = 10.dp),
            onClick = {
                onClickButton()
            }
        ) {
            Text(text = stringResource(R.string.see_trailer), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(16.dp))
        EMovieDivider()
    }
}

@Composable
private fun OptionCard(text: String, backgroundColor: Color, textColor: Color = Color.Black) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.wrapContentSize(),
        backgroundColor = backgroundColor,
    ) {
        Text(
            text = text,
            Modifier.padding(
                vertical = 8.dp,
                horizontal = 10.dp
            ),
            style = TextStyle(
                color = textColor
            )
        )
    }
}

@Composable
private fun Body(
    movieDetail: Movie
) {
    Column {
        Text(
            text = stringResource(R.string.movie_plot),
            style = MaterialTheme.typography.h6,
            modifier = HzPadding
        )
        Spacer(Modifier.height(26.dp))
        var seeMore by remember { mutableStateOf(true) }
        Text(
            text = movieDetail.overview,
            style = MaterialTheme.typography.body1,
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
            modifier = Modifier
                .heightIn(20.dp)
                .fillMaxWidth()
                .padding(top = 15.dp)
                .clickable {
                    seeMore = !seeMore
                }
        )
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

private fun openYoutubeLink(youtubeID: String?, context: Context) {
    youtubeID?.let {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeID"))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("$BASE_YOUTUBE_URL$youtubeID"))
        try {
            context.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(intentBrowser)
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
private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
private const val BASE_YOUTUBE_URL = "http://www.youtube.com/watch?v="
