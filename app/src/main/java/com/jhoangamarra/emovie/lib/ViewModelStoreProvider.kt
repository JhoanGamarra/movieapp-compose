package com.jhoangamarra.emovie.lib

import androidx.compose.runtime.staticCompositionLocalOf

val LocalViewModelStore = staticCompositionLocalOf<ViewModelStore> {
    error("ViewModel must be provided first")
}
