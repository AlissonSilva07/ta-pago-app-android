package br.alisson.edu.tapago.presentation.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.presentation.ui.analytics.AnalyticsEvent
import br.alisson.edu.tapago.presentation.ui.analytics.AnalyticsViewModel
import br.alisson.edu.tapago.presentation.ui.home.components.AcessoRapidoCard
import br.alisson.edu.tapago.presentation.ui.home.components.HomeHeader
import br.alisson.edu.tapago.presentation.ui.home.components.ProgressoContasCard
import br.alisson.edu.tapago.presentation.ui.home.components.ResumoContasCard
import br.alisson.edu.tapago.presentation.ui.home.components.TotalContasCard
import br.alisson.edu.tapago.presentation.ui.user.UserViewModel
import com.composables.icons.lucide.CopyPlus
import com.composables.icons.lucide.DollarSign
import com.composables.icons.lucide.Lucide

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToPay: () -> Unit = {},
    onNavigateToCreate: () -> Unit = {},
    userViewModel: UserViewModel = hiltViewModel(),
    analyticsViewModel: AnalyticsViewModel = hiltViewModel()
) {
    val userState = userViewModel.state.collectAsState()
    val analyticsState = analyticsViewModel.state.collectAsState()
    val expenses = analyticsState.value.summaryUnpaidExpenses
    val totalExpenses = analyticsState.value.totalExpensesMonth
    val monthlyExpenseProgress = analyticsState.value.montlhyExpenseProgress

    LaunchedEffect(Unit) {
        analyticsViewModel.onEvent(AnalyticsEvent.GetSummaryUnpaidExpenses)
        analyticsViewModel.onEvent(AnalyticsEvent.GetTotalExpenses)
        analyticsViewModel.onEvent(AnalyticsEvent.GetMonthlyExpenseProgress)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        HomeHeader(
            name = userState.value.userData?.name,
            avatar = userState.value.userData?.profilePicture
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Acesso rápido",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AcessoRapidoCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .clickable { onNavigateToPay() },
                        icon = Lucide.DollarSign,
                        title = "Pagar"
                    )
                    AcessoRapidoCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .clickable { onNavigateToCreate() },
                        icon = Lucide.CopyPlus,
                        title = "Adicionar"
                    )
                }
            }

            ResumoContasCard(expenses ?: emptyList())

            if (totalExpenses != null) {
                if (totalExpenses.isNotEmpty()) {
                    TotalContasCard(totalExpenses)
                }
            }

            if (monthlyExpenseProgress != null) {
                ProgressoContasCard(monthlyExpenseProgress)
            }
        }
    }
}