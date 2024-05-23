package com.example.lab3comp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.lab3comp.Data.ItemViewModel
import com.example.lab3comp.Drawer.AppBar
import com.example.lab3comp.Drawer.DrawerBody
import com.example.lab3comp.Drawer.DrawerHeader
import com.example.lab3comp.Drawer.MenuItem
import com.example.lab3comp.Screens.AddItemScreen
import com.example.lab3comp.Screens.EditItemScreen
import com.example.lab3comp.Screens.FilmListScreen
import com.example.lab3comp.Screens.HomeScreen
import com.example.lab3comp.Screens.InfoScreen
import com.example.lab3comp.Screens.ItemDetailsScreen
import com.example.lab3comp.Screens.SwapImageScreen


import com.example.lab3comp.ui.theme.Lab3CompTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3CompTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState() // to toggle navigation drawer we need navigation drawer state
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                             TopAppBar(
                                 backgroundColor = MaterialTheme.colorScheme.primary,
                                 navigationIcon = {
                                     IconButton(onClick = {
                                         scope.launch {
                                             scaffoldState.drawerState.open() //to open navigation drawer, need coroutine
                                         }
                                     }) {
                                         Icon(
                                             imageVector = Icons.Filled.Menu,
                                             contentDescription = "Localized description"
                                         )
                                     }
                                 },
                                 title = {
                                     Text(
                                         "Film Reviewer",
                                         maxLines = 1,
                                         overflow = TextOverflow.Ellipsis,
                                         fontSize = 20.sp,
                                         fontWeight = FontWeight.Bold
                                     )
                                 },

                             )
                    },
                    drawerContent = {
                        Column(
                        ) {
                            DrawerHeader()
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "home",
                                        title = "Home",
                                        contentDescription = "Go to home screen",
                                        icon = Icons.Filled.Home
                                    ),
                                    MenuItem(
                                        id = "swipe",
                                        title = "Swap image",
                                        contentDescription = "Go to swipe screen",
                                        icon = Icons.Default.ArrowForward
                                    ),
                                    MenuItem(
                                        id = "list",
                                        title = "Film list",
                                        contentDescription = "Go to film list screen",
                                        icon = Icons.Default.List
                                    ),
                                    MenuItem(
                                        id = "info",
                                        title = "Info",
                                        contentDescription = "Go to information screen",
                                        icon = Icons.Default.Info
                                    ),
                                ),
                                onItemClick = {selectedItem ->
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                    }
                                    when (selectedItem.id) {
                                        "home" -> navController.navigate(Screen.HomeScreen.route + "/{page}") {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        "swipe" -> navigateToScreen(navController, Screen.SwapImageScreen)
                                        "list" -> navigateToScreen(navController, Screen.FilmListScreen)
                                        "info" -> navigateToScreen(navController, Screen.InfoScreen)
                                        else -> println("Unknown menu item clicked")
                                    }
                                }
                            )
                        }
                    },

                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            elevation = 10.dp
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            val items = listOf(
                                Screen.SwapImageScreen,
                                Screen.HomeScreen,
                                Screen.FilmListScreen
                            )

                            items.forEach { screen ->
                                BottomNavigationItem(
                                    selectedContentColor = Color.Green,
                                    unselectedContentColor = Color.Gray,
                                    icon = {
                                            when (screen) {
                                                Screen.SwapImageScreen -> Icon(Icons.Filled.ArrowForward, contentDescription = null)
                                                Screen.HomeScreen -> Icon(Icons.Filled.Home, contentDescription = null)
                                                Screen.FilmListScreen -> Icon(Icons.Filled.List, contentDescription = null)
                                                else -> {}
                                            }
                                           },
                                    label = { Text(text = stringResource(screen.resourceId))},
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        when (screen) {
                                            Screen.SwapImageScreen -> {
                                                navController.navigate(screen.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                            Screen.HomeScreen -> {
                                                navController.navigate(Screen.HomeScreen.route + "/{page}") {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                            Screen.FilmListScreen -> {
                                                navController.navigate(screen.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                            else -> {}
                                        }

                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Screen.HomeScreen.route + "/{page}", Modifier.padding(innerPadding)) {
                        composable(
                            route = Screen.HomeScreen.route + "/{page}",
                            arguments = listOf(
                                navArgument("page") {
                                    type = NavType.StringType
                                    defaultValue = null
                                    nullable = true
                                })) {
                            val page = it.arguments?.getString("page")
                            HomeScreen(page)
                        }
                        composable(route = Screen.FilmListScreen.route) {FilmListScreen(navController, itemViewModel) }
                        composable("swapImage") { SwapImageScreen(navController) }
                        composable("info") { InfoScreen(navController) }
                        composable(
                            route = Screen.ItemDetailsScreen.route + "/{itemId}",
                            arguments = listOf(
                                navArgument("itemId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                            })) {
                                val itemId = it.arguments?.getString("itemId")
                                ItemDetailsScreen(itemId, navController, itemViewModel)
                            }
                        composable("addItem"){ AddItemScreen(navController, itemViewModel)}
                        composable(
                            route = Screen.EditItemScreen.route + "/{itemId}",
                            arguments = listOf(
                                navArgument("itemId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                })) {
                            val itemId = it.arguments?.getString("itemId")
                            EditItemScreen(itemId, navController, itemViewModel)
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object HomeScreen : Screen("home", R.string.home)
    object FilmListScreen : Screen("filmList", R.string.filmList)
    object SwapImageScreen : Screen("swapImage", R.string.swapImage)
    object InfoScreen: Screen("info", R.string.info)
    object AddItemScreen: Screen("addItem", R.string.addItem)
    object ItemDetailsScreen: Screen("itemDetails", R.string.itemDetails)
    object EditItemScreen: Screen("editItem", R.string.editItem)

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}




