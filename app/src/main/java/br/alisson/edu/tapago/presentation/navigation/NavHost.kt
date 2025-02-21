package br.alisson.edu.tapago.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.alisson.edu.tapago.presentation.login.LoginScreen
import br.alisson.edu.tapago.presentation.signup.SignupScreen
import br.alisson.edu.tapago.presentation.splash.SplashScreen
import br.alisson.edu.tapago.presentation.welcome.WelcomeScreen

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
            WelcomeScreen(
                navigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                navigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                },
                navigateToHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Home.route) {
            SignupScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}