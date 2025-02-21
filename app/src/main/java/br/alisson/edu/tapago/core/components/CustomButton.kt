package br.alisson.edu.tapago.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    title: String,
    variant: ButtonVariant,
    disabled: Boolean,
    onClick: () -> Unit,
) {

    val backgroundColor = when (variant) {
        ButtonVariant.DEFAULT -> {
            MaterialTheme.colorScheme.secondaryContainer
        }
        ButtonVariant.DISABLED -> MaterialTheme.colorScheme.onPrimary
        ButtonVariant.MUTED -> MaterialTheme.colorScheme.onPrimary
    }

    val contentColor = when (variant) {
        ButtonVariant.DISABLED -> MaterialTheme.colorScheme.background
        ButtonVariant.MUTED -> MaterialTheme.colorScheme.background
        else -> MaterialTheme.colorScheme.primaryContainer
    }

    Button(
        onClick = {
            if (!disabled) {
                onClick()
            }
        },
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.background,
        ),
        enabled = variant != ButtonVariant.DISABLED,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

enum class ButtonVariant {
    DEFAULT,
    DISABLED,
    MUTED
}