package br.alisson.edu.tapago.presentation.ui.pay.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.presentation.utils.formatDateAdapter
import com.composables.icons.lucide.ArrowUpRight
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onNavigateToDetails(expense.id) }
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
                verticalAlignment = Alignment.Top,
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Abrir",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontWeight = FontWeight.Normal
                    )
                    Icon(
                        imageVector = Lucide.ArrowUpRight,
                        contentDescription = "Icon",
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}