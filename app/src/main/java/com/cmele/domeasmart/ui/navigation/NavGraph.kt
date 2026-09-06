package com.cmele.domeasmart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmele.domeasmart.ui.screens.CameraScreen
import com.cmele.domeasmart.ui.screens.HomeScreen
import com.cmele.domeasmart.ui.screens.SensoriScreen
import com.cmele.domeasmart.ui.screens.SettingsScreen
import com.cmele.domeasmart.ui.screens.StanzeScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Stanze : Screen("stanze")
    data object Sensori : Screen("sensori")
    data object Camera : Screen("camera")
    data object Settings : Screen("settings")  // NUOVO
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Stanze.route) { StanzeScreen() }
        composable(Screen.Sensori.route) { SensoriScreen() }
        composable(Screen.Camera.route) { CameraScreen() }
        composable(Screen.Settings.route) {  // NUOVO
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}