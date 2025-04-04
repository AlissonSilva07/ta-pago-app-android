package br.alisson.edu.tapago.presentation.ui.pay

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.presentation.components.ChipActions
import br.alisson.edu.tapago.presentation.components.CustomChip
import br.alisson.edu.tapago.presentation.components.CustomSearchBar
import br.alisson.edu.tapago.presentation.components.chipTypes
import br.alisson.edu.tapago.presentation.ui.pay.components.ContaItemCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PayScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: PayScreenViewModel = hiltViewModel()
) {
    val expensesState = viewModel.state.collectAsState()

    val expenses = expensesState.value.expenses
    val isRefreshing = expensesState.value.isRefreshing

    var selectedChip by remember { mutableStateOf(chipTypes.first().id) }

    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(PayScreenEvent.GetExpenses)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchBar(
            value = expensesState.value.search ?: "",
            onValueChange = { newValue ->
                viewModel.onEvent(PayScreenEvent.UpdateSearch(newValue))
            },
            isSearching = expensesState.value.isSearching,
            modifier = Modifier.fillMaxWidth()
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
                            ChipActions.TODOS -> viewModel.onEvent(
                                PayScreenEvent.UpdateSort(
                                    sortBy = "dueDate",
                                    sortOrder = "desc"
                                )
                            )

                            ChipActions.PROXIMOS -> viewModel.onEvent(
                                PayScreenEvent.UpdateSort(
                                    sortBy = "dueDate",
                                    sortOrder = "desc"
                                )
                            )

                            ChipActions.DISTANTES -> viewModel.onEvent(
                                PayScreenEvent.UpdateSort(
                                    sortBy = "dueDate",
                                    sortOrder = "asc"
                                )
                            )

                            ChipActions.ORDEM_AZ -> viewModel.onEvent(
                                PayScreenEvent.UpdateSort(
                                    sortBy = "title",
                                    sortOrder = "asc"
                                )
                            )

                            ChipActions.ORDEM_ZA -> viewModel.onEvent(
                                PayScreenEvent.UpdateSort(
                                    sortBy = "title",
                                    sortOrder = "desc"
                                )
                            )
                        }
                    }
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        PullToRefreshBox(
            modifier = Modifier.weight(1f),
            state = refreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                coroutineScope.launch {
                    viewModel.onEvent(PayScreenEvent.RefreshExpenses)
                }
            },
        ) {
            when {
                expensesState.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                }

                expenses.isNotEmpty() -> {
                    LazyColumn(
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

                else -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum gasto encontrado.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                }
            }
        }
    }
}