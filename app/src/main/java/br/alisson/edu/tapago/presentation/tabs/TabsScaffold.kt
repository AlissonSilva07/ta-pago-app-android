package br.alisson.edu.tapago.presentation.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.alisson.edu.tapago.presentation.tabs.components.BottomNavBar
import br.alisson.edu.tapago.presentation.tabs.components.BottomNavItem
import br.alisson.edu.tapago.presentation.tabs.home.HomeScreen
import br.alisson.edu.tapago.presentation.tabs.menu.MenuScreen
import br.alisson.edu.tapago.presentation.tabs.pay.PayScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsScaffold() {
    val tabNavController = rememberNavController()

    val navBackStackEntry = tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val title = when (currentRoute) {
        BottomNavItem.Home.route -> "Home"
        BottomNavItem.Pay.route -> "Gastos"
        BottomNavItem.Menu.route -> "Menu"
        else -> ""
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            BottomNavBar(tabNavController)
        },
        topBar = {
            if (currentRoute != BottomNavItem.Home.route) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    colors = TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        scrolledContainerColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = tabNavController,
            startDestination = BottomNavItem.Home.route
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                )
            }

            composable(BottomNavItem.Pay.route) {
                PayScreen(modifier = Modifier.padding(padding))
            }

            composable(BottomNavItem.Menu.route) {
                MenuScreen(modifier = Modifier.padding(padding))
            }
        }
    }
}