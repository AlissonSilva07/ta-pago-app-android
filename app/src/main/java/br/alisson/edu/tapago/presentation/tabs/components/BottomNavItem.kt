package br.alisson.edu.tapago.presentation.tabs.components

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.DollarSign
import com.composables.icons.lucide.LayoutDashboard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User

sealed class BottomNavItem(val route: String, val icon: ImageVector) {
    data object Home : BottomNavItem("home", Lucide.LayoutDashboard)
    data object Pay : BottomNavItem(PayRoutes.Pay, Lucide.DollarSign)
    data object Menu : BottomNavItem("profile", Lucide.User)
}

object PayRoutes {
    const val Pay = "pay"
    const val PayItemDetails = "pay/{itemId}"

    fun getPayItemDetailsRoute(itemId: String): String {
        return "pay/$itemId"
    }
}