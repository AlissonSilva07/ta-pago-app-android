package br.alisson.edu.tapago.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object Welcome : Screen("welcome")
    data object Tabs : Screen("tabs")
    data object PayRoutes {
        const val Pay = "pay"
        const val PayItemDetails = "pay/{itemId}"
        const val PayCreate = "pay/create"

        fun getPayItemDetailsRoute(itemId: String): String {
            return "pay/$itemId"
        }
    }
}