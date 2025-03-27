package br.alisson.edu.tapago.presentation.tabs.pay

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.core.components.CustomSearchBar
import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.presentation.expenses.ExpensesEvent
import br.alisson.edu.tapago.presentation.expenses.ExpensesViewModel
import br.alisson.edu.tapago.presentation.tabs.pay.components.ChipActions
import br.alisson.edu.tapago.presentation.tabs.pay.components.ContaItemCard
import br.alisson.edu.tapago.presentation.tabs.pay.components.CustomChip
import br.alisson.edu.tapago.presentation.tabs.pay.components.chipTypes
import br.alisson.edu.tapago.presentation.ui.theme.TaPagoTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PayScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val expensesState = viewModel.state.collectAsState()
    val expenses = expensesState.value.expenses
    var selectedChip by remember { mutableStateOf(chipTypes.first().id) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Todos os Gastos",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        CustomSearchBar(
            value = expensesState.value.search ?: "",
            onValueChange = {
                viewModel.onEvent(ExpensesEvent.UpdateSearch(it))
            }
        )
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(chipTypes) { item ->
                CustomChip(
                    item = item,
                    selectedChip = selectedChip,
                    selectedChipChanged = {
                        selectedChip = item.id
                        when (item.action) {
                            ChipActions.TODOS -> viewModel.onEvent(ExpensesEvent.UpdateSort(sortBy = "dueDate", sortOrder = "desc"))
                            ChipActions.PROXIMOS -> viewModel.onEvent(ExpensesEvent.UpdateSort(sortBy = "dueDate", sortOrder = "desc"))
                            ChipActions.DISTANTES -> viewModel.onEvent(ExpensesEvent.UpdateSort(sortBy = "dueDate", sortOrder = "asc"))
                            ChipActions.ORDEM_AZ -> viewModel.onEvent(ExpensesEvent.UpdateSort(sortBy = "title", sortOrder = "asc"))
                            ChipActions.ORDEM_ZA -> viewModel.onEvent(ExpensesEvent.UpdateSort(sortBy = "title", sortOrder = "desc"))
                        }
                    }
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(expenses) { item ->
                ContaItemCard(
                    expense = item,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }
    }
}