package br.alisson.edu.tapago.presentation.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.alisson.edu.tapago.presentation.tabs.components.BottomNavBar
import br.alisson.edu.tapago.presentation.tabs.components.BottomNavItem
import br.alisson.edu.tapago.presentation.tabs.components.PayRoutes
import br.alisson.edu.tapago.presentation.tabs.home.HomeScreen
import br.alisson.edu.tapago.presentation.tabs.menu.MenuScreen
import br.alisson.edu.tapago.presentation.tabs.pay.PayCreateScreen
import br.alisson.edu.tapago.presentation.tabs.pay.PayItemDetailsScreen
import br.alisson.edu.tapago.presentation.tabs.pay.PayScreen
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsScaffold() {
    val tabNavController = rememberNavController()

    val navBackStackEntry = tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val title = when (currentRoute) {
        BottomNavItem.Home.route -> "Home"
        PayRoutes.Pay -> "Gastos"
        PayRoutes.PayCreate -> "Novo Gasto"
        PayRoutes.PayItemDetails -> "Detalhes do Gasto"
        BottomNavItem.Menu.route -> "Menu"
        else -> ""
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){ data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface,
                    actionColor = MaterialTheme.colorScheme.surface
                )
            }
        },
        bottomBar = {
            BottomNavBar(tabNavController)
        },
        topBar = {
            if (currentRoute != BottomNavItem.Home.route) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 32.dp),
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
                    navigationIcon = {
                        if (currentRoute == PayRoutes.PayItemDetails || currentRoute == PayRoutes.PayCreate) {
                            IconButton(onClick = { tabNavController.popBackStack() }) {
                                Icon(
                                    imageVector = Lucide.ChevronLeft,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
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
                        .fillMaxSize(),
                    onNavigateToPay = {
                        tabNavController.navigate(PayRoutes.Pay)
                    },
                    onNavigateToCreate = {
                        tabNavController.navigate(PayRoutes.PayCreate)
                    }
                )
            }

            composable(PayRoutes.Pay) {
                PayScreen(
                    modifier = Modifier.padding(padding),
                    onNavigateToDetails = { itemId ->
                        tabNavController.navigate(PayRoutes.getPayItemDetailsRoute(itemId))
                    },
                    onNavigateToCreate = {
                        tabNavController.navigate(PayRoutes.PayCreate)
                    }
                )
            }

            composable(PayRoutes.PayCreate) {
                PayCreateScreen(
                    modifier = Modifier.padding(padding),
                    onNavigateBack = {
                        tabNavController.navigate(PayRoutes.Pay)
                    },
                    showSnackbar = { message ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                )
            }

            composable(
                route = PayRoutes.PayItemDetails,
                arguments = listOf(navArgument("itemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId")
                PayItemDetailsScreen(
                    modifier = Modifier.padding(padding),
                    itemId = itemId,
                    onNavigateBack = {
                        tabNavController.popBackStack()
                    },
                    showSnackbar = { message ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                )
            }

            composable(BottomNavItem.Menu.route) {
                MenuScreen(modifier = Modifier.padding(padding))
            }
        }
    }
}
