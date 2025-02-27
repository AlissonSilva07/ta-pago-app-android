package br.alisson.edu.tapago.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object Welcome : Screen("welcome")
    data object Tabs : Screen("tabs")

    data object Home : Screen("home")
}