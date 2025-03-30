package br.alisson.edu.tapago.presentation.components

import androidx.compose.ui.graphics.vector.ImageVector
import br.alisson.edu.tapago.presentation.navigation.Screen
import com.composables.icons.lucide.DollarSign
import com.composables.icons.lucide.LayoutDashboard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User

sealed class BottomNavItem(val route: String, val icon: ImageVector) {
    data object Home : BottomNavItem("home", Lucide.LayoutDashboard)
    data object Pay : BottomNavItem(Screen.PayRoutes.Pay, Lucide.DollarSign)
    data object Menu : BottomNavItem("profile", Lucide.User)
}