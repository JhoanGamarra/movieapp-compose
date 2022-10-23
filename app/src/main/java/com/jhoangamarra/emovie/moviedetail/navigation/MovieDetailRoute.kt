package com.jhoangamarra.emovie.moviedetail.navigation

import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.jhoangamarra.emovie.lib.navigation.Route

object MovieDetailRoute : Route(route = Route, uriPatternSuffix = UriPatternSuffix) {

    override fun getDeeplinkNavigationRoute(arguments: Bundle?): Uri {
        require(arguments != null && arguments.containsKey(IdArgument))
        return getUriPattern().replace(
            "{$IdArgument}",
            arguments.getLong(
                IdArgument
            ).toString()
        ).toUri()
    }


    fun navigate(id: Long, navController: NavController) {
        navigate(navController,
            Bundle().apply {
                putLong(IdArgument, id)
            }
        )
    }

    const val IdArgument = "id"

}

private const val Route = "movie_detail"
private const val UriPatternSuffix = "$Route/{${MovieDetailRoute.IdArgument}}"