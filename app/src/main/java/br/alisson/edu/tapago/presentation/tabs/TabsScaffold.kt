package br.alisson.edu.tapago.presentation.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.alisson.edu.tapago.presentation.tabs.home.HomeScreen
import com.composables.icons.lucide.*
import com.example.compose.TaPagoTheme

@Composable
fun TabsScaffold(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Pay, BottomNavItem.Menu)


    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->
        HomeScreen(
            modifier = Modifier
                .padding(padding)
        )
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Lucide.House, "Home")
    object Pay : BottomNavItem("search", Lucide.DollarSign, "Search")
    object Menu : BottomNavItem("profile", Lucide.User, "Profile")
}