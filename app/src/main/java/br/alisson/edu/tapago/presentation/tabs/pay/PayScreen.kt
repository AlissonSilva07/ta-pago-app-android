package br.alisson.edu.tapago.presentation.tabs.pay

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.core.components.CustomSearchBar
import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.presentation.expenses.ExpensesViewModel
import br.alisson.edu.tapago.presentation.tabs.pay.components.ContaItemCard
import br.alisson.edu.tapago.presentation.tabs.pay.components.CustomChip
import br.alisson.edu.tapago.presentation.tabs.pay.components.chipTypes
import br.alisson.edu.tapago.presentation.ui.theme.TaPagoTheme

@Composable
fun PayScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val expensesState = viewModel.state.collectAsState()
    val expenses = expensesState.value.expenses
    var selectedChip by remember { mutableStateOf(chipTypes.first().id) }

    val expensesList = listOf(
        Expense(
            id = "1",
            title = "Conta Abril",
            category = "Alimentacao",
            isPaid = false,
            userId = "1",
            description = "Descricao teste",
            createdAt = "2025-08-04",
            amount = 500,
            dueDate = "2025-08-09"
        ),
        Expense(
            id = "1",
            title = "Conta Abril",
            category = "Alimentacao",
            isPaid = false,
            userId = "1",
            description = "Descricao teste",
            createdAt = "2025-08-04",
            amount = 500,
            dueDate = "2025-08-09"
        ),
        Expense(
            id = "1",
            title = "Conta Abril",
            category = "Alimentacao",
            isPaid = false,
            userId = "1",
            description = "Descricao teste",
            createdAt = "2025-08-04",
            amount = 500,
            dueDate = "2025-08-09"
        ),Expense(
            id = "1",
            title = "Conta Abril",
            category = "Alimentacao",
            isPaid = false,
            userId = "1",
            description = "Descricao teste",
            createdAt = "2025-08-04",
            amount = 500,
            dueDate = "2025-08-09"
        ),Expense(
            id = "1",
            title = "Conta Abril",
            category = "Alimentacao",
            isPaid = false,
            userId = "1",
            description = "Descricao teste",
            createdAt = "2025-08-04",
            amount = 500,
            dueDate = "2025-08-09"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
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
            value = "",
            onValueChange = {}
        )
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(chipTypes) { item ->
                CustomChip(
                    item = item,
                    selectedChip = selectedChip,
                    selectedChipChanged = { selectedChip = item.id }
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        if (expenses != null) {
            LazyColumn (
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(expenses) { item ->
                    ContaItemCard(
                        expense = item
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PayScreenPrev() {
    TaPagoTheme {
        PayScreen()
    }
}