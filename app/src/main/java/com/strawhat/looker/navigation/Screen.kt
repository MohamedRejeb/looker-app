package com.strawhat.looker.navigation

sealed class Screen(val route: String) {
    object Splash: Screen(route = "splash_screen")
    object Login: Screen(route = "login_screen")
    object Register: Screen(route = "register_screen")

    object Map: Screen(route = "map_screen")
    object Discussions: Screen(route = "discussions_screen")
    object Discussion: Screen(route = "discussion_screen")
    object Search: Screen(route = "search_screen")
    object Profile: Screen(route = "profile_screen")
}
