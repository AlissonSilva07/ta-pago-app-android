package br.alisson.edu.tapago.presentation.tabs.components

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.DollarSign
import com.composables.icons.lucide.LayoutDashboard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User

sealed class BottomNavItem(val route: String, val icon: ImageVector) {
    data object Home : BottomNavItem("home", Lucide.LayoutDashboard)
    data object Pay : BottomNavItem("pay", Lucide.DollarSign)
    data object Menu : BottomNavItem("profile", Lucide.User)
}