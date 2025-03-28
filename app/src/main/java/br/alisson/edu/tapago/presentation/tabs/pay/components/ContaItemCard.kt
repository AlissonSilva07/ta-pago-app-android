package br.alisson.edu.tapago.presentation.tabs.pay.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.utils.formatDateAdapter
import com.composables.icons.lucide.HousePlug
import com.composables.icons.lucide.Lucide
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContaItemCard(
    expense: Expense,
    onNavigateToDetails: (String) -> Unit = {},
) {
    val status = when (expense.isPaid) {
        true -> "Pago"
        false -> "Não Pago"
    }

    val due = when {
        Instant.parse(expense.dueDate).isBefore(Instant.now()) && status == "Pago" -> "Pago"
        Instant.parse(expense.dueDate).isBefore(Instant.now()) && status == "Não Pago" -> "Vencido"
        Instant.parse(expense.dueDate).isAfter(Instant.now()) && status == "Pago" -> "Pago"
        Instant.parse(expense.dueDate).isAfter(Instant.now()) && status == "Não Pago" -> "A vencer"
        else -> "Vence hoje"
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(
                        modifier = Modifier.size(52.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Lucide.HousePlug,
                            contentDescription = "Icon",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = expense.title,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "R$ ${expense.amount}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Vencimento: ${formatDateAdapter(expense.dueDate)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Status: $due",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontWeight = FontWeight.Normal
                    )
                }

                TextButton(
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.inverseSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .padding(0.dp),
                    onClick = { onNavigateToDetails(expense.id) }
                ) {
                    Text(
                        text = "Abrir",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}