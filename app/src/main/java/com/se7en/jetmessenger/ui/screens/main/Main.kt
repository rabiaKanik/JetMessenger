package com.se7en.jetmessenger.ui.screens.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.compose.*
import com.se7en.jetmessenger.data.models.User
import com.se7en.jetmessenger.ui.backPressHandler
import com.se7en.jetmessenger.ui.screens.Routing
import com.se7en.jetmessenger.viewmodels.UsersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun Routing.Main.Content(
    usersViewModel: UsersViewModel,
    onChatClick: (user: User) -> Unit,
    onSearchClick: () -> Unit
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    fun currentRouting(): Routing.Main.BottomNav? {
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        return bottomNavRoutings.find { it.route == currentRoute }
    }

    val users: List<User> by usersViewModel.users.collectAsState()

    var storyVisible by remember { mutableStateOf(false) }
    backPressHandler(
        enabled = storyVisible,
        onBackPressed = { storyVisible = false }
    )

    Box {
        Scaffold(
            topBar = {
                currentRouting()?.let {
                    TopBar(currentRouting = it, onActionClick = { })
                }
            },
            bottomBar = {
                currentRouting()?.let {
                    BottomBar(
                        currentRouting = it,
                        onSelected = { selectedRouting ->
                            if (it.route != selectedRouting.route) {
                                if(selectedRouting is Routing.Main.BottomNav.Chats)
                                    bottomNavController.popBackStack(bottomNavController.graph.startDestination, false)
                                else
                                    bottomNavController.navigate(selectedRouting.route)
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.padding(innerPadding),
                color = MaterialTheme.colors.surface
            ) {
                // TODO: fix BottomNav state when navigating back from another screen (like navigating back from Conversation to Main)
                NavHost(
                    navController = bottomNavController,
                    startDestination = Routing.Main.BottomNav.Chats.route
                ) {
                    composable(Routing.Main.BottomNav.Chats.route) {
                        Routing.Main.BottomNav.Chats.Content(
                            users,
                            onChatClick,
                            onSearchClick
                        )
                    }
                }
            }
        }

    }
}
