package com.example.lab3comp

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun navigateToScreen(
    navController: NavController,
    screen: Screen,
) {
    navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}