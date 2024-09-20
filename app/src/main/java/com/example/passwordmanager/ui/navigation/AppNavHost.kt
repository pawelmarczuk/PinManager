package com.example.passwordmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanager.ui.password.list.PasswordsListScreen
import com.example.passwordmanager.ui.password.newElement.NewPasswordScreen

enum class ScreenRoutes {
    List,
    Item,
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.List.name,
    ) {
        composable(route = ScreenRoutes.List.name) {
            PasswordsListScreen(
                onAddNewItemClicked = {
                    navController.navigate(ScreenRoutes.Item.name)
                },
            )
        }
        composable(route = ScreenRoutes.Item.name) {
            NewPasswordScreen(
                navController = navController,
                onExit = {
                    navController.popBackStack(ScreenRoutes.List.name, inclusive = false)
                },
            )
        }
    }
}

