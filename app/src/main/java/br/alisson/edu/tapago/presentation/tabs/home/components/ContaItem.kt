package br.alisson.edu.tapago.presentation.tabs.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.domain.model.Expense
import com.composables.icons.lucide.HousePlug
import com.composables.icons.lucide.Lucide

@Composable
fun ContaItem(
    expense: Expense? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.inverseSurface,
                    disabledContentColor = MaterialTheme.colorScheme.surface
                ),
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Lucide.HousePlug,
                        contentDescription = "Icon",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = expense?.title ?: "Título",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = expense?.dueDate ?: "Data",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Text(
            text = expense?.amount.toString() ?: "Valor",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontWeight = FontWeight.Normal
        )
    }
}