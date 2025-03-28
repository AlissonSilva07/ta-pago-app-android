package br.alisson.edu.tapago.presentation.tabs.pay

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.core.components.ButtonVariant
import br.alisson.edu.tapago.core.components.CustomButton
import br.alisson.edu.tapago.presentation.expenses.ExpensesEvent
import br.alisson.edu.tapago.presentation.expenses.ExpensesViewModel
import com.composables.icons.lucide.HousePlug
import com.composables.icons.lucide.Lucide

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PayItemDetailsScreen(
    modifier: Modifier = Modifier,
    itemId: String?,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val expense = state.value.expenseById

    LaunchedEffect(Unit) {
        Log.d("PayItemDetailsScreen", "PayItemDetailsScreen: $itemId")
        if (itemId != null) {
            viewModel.onEvent(ExpensesEvent.GetExpenseById(itemId))
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Box(
                            modifier = Modifier.size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Lucide.HousePlug,
                                contentDescription = "Icon",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nome",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = expense?.title ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    CardContent("Valor", "R$ ${expense?.amount}")
                    CardContent("Vencimento", expense?.dueDate ?: "")
                    CardContent("Status", "Não Pago")
                }
            }
        }

        // Buttons (Non-scrollable)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                title = "Tá Pago",
                variant = ButtonVariant.DEFAULT,
                disabled = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            CustomButton(
                title = "Excluir Gasto",
                variant = ButtonVariant.DESTRUCTIVE,
                disabled = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CardContent(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
