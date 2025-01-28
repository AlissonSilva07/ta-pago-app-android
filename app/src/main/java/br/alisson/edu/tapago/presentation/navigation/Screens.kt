package br.alisson.edu.tapago.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Welcome : Screen("welcome")
}