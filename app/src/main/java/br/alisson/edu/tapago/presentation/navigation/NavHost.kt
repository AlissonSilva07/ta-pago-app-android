package br.alisson.edu.tapago.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.alisson.edu.tapago.presentation.screens.LoginScreen
import br.alisson.edu.tapago.presentation.screens.SplashScreen
import br.alisson.edu.tapago.presentation.screens.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navigateToWelcome = {
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(navigateToLogin = {
                navController.navigate(Screen.Login.route)
            })
        }

        composable(Screen.Login.route) {
            LoginScreen()
        }
    }
}