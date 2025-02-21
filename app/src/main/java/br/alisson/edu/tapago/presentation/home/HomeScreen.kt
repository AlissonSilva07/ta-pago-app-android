package br.alisson.edu.tapago.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HomeScreen() {
    Column {
        Text(
            text = "Home",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.surface,
            fontWeight = FontWeight.Bold
        )
    }
}