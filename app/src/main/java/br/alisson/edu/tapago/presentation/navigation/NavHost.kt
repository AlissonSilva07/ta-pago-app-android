package br.alisson.edu.tapago.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.alisson.edu.tapago.presentation.ui.login.LoginScreen
import br.alisson.edu.tapago.presentation.ui.login.LoginViewModel
import br.alisson.edu.tapago.presentation.ui.signup.SignupScreen
import br.alisson.edu.tapago.presentation.ui.splash.SplashScreen
import br.alisson.edu.tapago.presentation.ui.welcome.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavHost(loginViewModel: LoginViewModel) {
    val navController = rememberNavController()
    val authToken by loginViewModel.authToken.collectAsState()

    val startDestination = if (authToken.isNullOrEmpty()) {
        Screen.Splash.route
    } else {
        Screen.Tabs.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
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
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
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
                    navController.navigate(Screen.Tabs.route) {
                        popUpTo(0)
                    }
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

        composable(Screen.Tabs.route) {
            TabsScaffold()
        }
    }
}