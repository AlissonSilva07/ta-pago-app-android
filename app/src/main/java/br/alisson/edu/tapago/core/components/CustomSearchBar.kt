package br.alisson.edu.tapago.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search

@Composable
fun CustomSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Pesquise por Gasto"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            cursorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            focusedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontWeight = FontWeight.Normal
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Lucide.Search,
                contentDescription = "Toggle Password Visibility",
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}
