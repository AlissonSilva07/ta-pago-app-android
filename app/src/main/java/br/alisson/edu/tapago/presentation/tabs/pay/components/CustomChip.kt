package br.alisson.edu.tapago.presentation.tabs.pay.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

enum class ChipActions {
    TODOS, PROXIMOS, DISTANTES, ORDEM_AZ, ORDEM_ZA
}

data class ChipType(
    val id: Number,
    val label: String,
    val action: ChipActions,
)

@Composable
fun CustomChip(
    item: ChipType,
    selectedChip: Number,
    selectedChipChanged: (Number) -> Unit
) {
    FilterChip(
        selected = item.id == selectedChip,
        label = {
            Text(
                text = item.label,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        },
        shape = RoundedCornerShape(4.dp),
        colors = SelectableChipColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            selectedLabelColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            leadingIconColor = MaterialTheme.colorScheme.onSurface,
            trailingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledSelectedContainerColor = MaterialTheme.colorScheme.onSurface,
            selectedLeadingIconColor = MaterialTheme.colorScheme.surface,
            selectedTrailingIconColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(width = 0.dp, color = MaterialTheme.colorScheme.surface),
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
        onClick = {
            selectedChipChanged(item.id)
        }
    )
}

val chipTypes = listOf(
    ChipType(
        id = 1,
        label = "Todos",
        action = ChipActions.TODOS
    ),
    ChipType(
        id = 2,
        label = "Vencimento Próximo",
        action = ChipActions.PROXIMOS
    ),
    ChipType(
        id = 3,
        label = "Vencimento Distante",
        action = ChipActions.DISTANTES
    ),
    ChipType(
        id = 4,
        label = "Ordem: A-Z",
        action = ChipActions.ORDEM_AZ
    ),
    ChipType(
        id = 5,
        label = "Ordem: Z-A",
        action = ChipActions.ORDEM_ZA
    )
)
