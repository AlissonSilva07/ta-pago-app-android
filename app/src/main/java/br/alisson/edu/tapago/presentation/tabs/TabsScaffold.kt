package br.alisson.edu.tapago.presentation.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.alisson.edu.tapago.presentation.tabs.components.BottomNavBar
import br.alisson.edu.tapago.presentation.tabs.components.BottomNavItem
import br.alisson.edu.tapago.presentation.tabs.home.HomeScreen
import br.alisson.edu.tapago.presentation.tabs.menu.MenuScreen
import br.alisson.edu.tapago.presentation.tabs.pay.PayScreen

@Composable
fun TabsScaffold() {
    val tabNavController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        bottomBar = {
            BottomNavBar(tabNavController)
        }
    ) { padding ->
        NavHost(
            navController = tabNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen()
            }

            composable(BottomNavItem.Pay.route) {
                PayScreen()
            }

            composable(BottomNavItem.Menu.route) {
                MenuScreen()
            }
        }
    }
}