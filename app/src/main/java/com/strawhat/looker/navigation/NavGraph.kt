package com.strawhat.looker.navigation

import com.strawhat.looker.R
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.strawhat.looker.auth.login.ui.LoginScreen
import com.strawhat.looker.auth.register.ui.RegisterScreen
import com.strawhat.looker.chat.ui.discussion.DiscussionScreen
import com.strawhat.looker.chat.ui.discussions.DiscussionsScreen
import com.strawhat.looker.map.ui.MapScreen
import com.strawhat.looker.navigation_bar.MainNavigationBarScreen
import com.strawhat.looker.navigation_bar.NavigationBarItemModel
import com.strawhat.looker.search.ui.SearchScreen
import com.strawhat.looker.splash.SplashScreen
import com.strawhat.looker.suggest.SuggestScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    var selectedRoute by remember {
        mutableStateOf("")
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        selectedRoute = destination.route ?: ""
    }

    val startDestination = Screen.Splash.route

    Column(
        modifier = modifier,
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.weight(1f),
        ) {
            composable(
                route = Screen.Splash.route
            ) {
                SplashScreen(
                    navigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToHome = {
                        navController.navigate(Screen.Map.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                )
            }

            composable(
                route = Screen.Login.route,
                enterTransition = {
                    if (initialState.destination.route == Screen.Register.route) {
                        null
                    } else {
                        slideInHorizontally(initialOffsetX = { 1000 })
                    }
                },
            ) {
                LoginScreen(
                    navigateToMap = {
                        navController.navigate(Screen.Map.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                )
            }

            composable(
                route = Screen.Register.route,
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { 1000 })
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { 1000 })
                }
            ) {
                RegisterScreen(
                    navigateToMap = {
                        navController.navigate(Screen.Map.route) {
                            popUpTo(Screen.Map.route)  { inclusive = true }
                        }
                    },
                    navigateToLogin = {
                        if (!navController.popBackStack()) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    },
                )
            }

            composable(
                route = Screen.Map.route,
                enterTransition = {
                    slideInVertically(initialOffsetY = { 1000 })
                }
            ) {
                MapScreen()
            }

            composable(
                route = Screen.Map.route + "/{placeId}",
                arguments = listOf(navArgument("placeId") { type = NavType.StringType })
            ) {
                MapScreen()
            }

            composable(
                route = Screen.Discussions.route
            ) {
                DiscussionsScreen(
                    navigateToDiscussion = { navController.navigate(Screen.Discussion.route) }
                )
            }

            composable(
                route = Screen.Suggest.route
            ) {
                SuggestScreen()
            }

            composable(
                route = Screen.Discussion.route
            ) {
                DiscussionScreen(
                    navigateUp = { navController.navigateUp() }
                )
            }

            composable(
                route = Screen.Search.route
            ) {
                SearchScreen(
                    navigateToMap = { placeId -> navController.navigate(Screen.Map.route + "/$placeId")  }
                )
            }

            composable(
                route = Screen.Profile.route
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {

                    Text(text = "Welcome to your profile")
                }
            }
        }

        if (mainNavigationBarItems.any { it.route in selectedRoute }) {
            val insets = WindowInsets

            val imeBottom = with(LocalDensity.current) { insets.ime.getBottom(this).toDp() }

            MainNavigationBarScreen(
                items = mainNavigationBarItems,
                selectedRoute = selectedRoute,
                onRouteSelected = { route ->
                    if (navController.currentDestination?.route != route) {
                        navController.navigate(route = route) {
                            popUpTo(route = Screen.Map.route) {
                                inclusive = false
                            }

                        }
                    }
                },
                modifier = Modifier.padding(bottom = imeBottom)
            )
        }
    }

}

private val mainNavigationBarItems = listOf(
    NavigationBarItemModel(
        label = "Map",
        route = Screen.Map.route,
        resourceId = R.drawable.location_fill,
    ),
    NavigationBarItemModel(
        label = "Chat",
        route = Screen.Discussions.route,
        resourceId = R.drawable.message_fill,
    ),
    NavigationBarItemModel(
        label = "Suggest",
        route = Screen.Suggest.route,
        resourceId = R.drawable.add,
    ),
    NavigationBarItemModel(
        label = "Search",
        route = Screen.Search.route,
        resourceId = R.drawable.search,
    ),
    NavigationBarItemModel(
        label = "Profile",
        route = Screen.Profile.route,
        resourceId = R.drawable.user_fill,
    ),
)