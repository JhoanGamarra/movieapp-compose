package com.jhoangamarra.emovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jhoangamarra.emovie.home.navigation.HomeRoute
import com.jhoangamarra.emovie.home.ui.homeScreen
import com.jhoangamarra.emovie.lib.LocalViewModelStore
import com.jhoangamarra.emovie.lib.ViewModelStore
import com.jhoangamarra.emovie.lib.ui.theme.EMovieTheme
import com.jhoangamarra.emovie.moviedetail.ui.movieDetailScreen
import com.jhoangamarra.emovie.splash.animationForSplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setOnExitAnimationListener { splashScreenView ->
            animationForSplashScreen(splashScreenView)
        }
        setContent {
            val navController = rememberNavController()
            val viewModelStore = remember { ViewModelStore() }

            EMovieTheme {
                CompositionLocalProvider(
                    LocalNavControllerProvider provides navController,
                    LocalViewModelStore provides viewModelStore
                ) {
                    Scaffold { innerPadding ->
                        NavHost(navController = navController, startDestination = HomeRoute.route, Modifier.padding(innerPadding)) {
                            homeScreen()
                            movieDetailScreen()
                        }
                    }
                }
            }
        }
    }
}

internal val LocalNavControllerProvider = staticCompositionLocalOf<NavController> {
    error("You must provide a NavController before attempt to use it")
}
