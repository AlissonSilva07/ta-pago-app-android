package br.alisson.edu.tapago.presentation.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.alisson.edu.tapago.presentation.tabs.home.HomeScreen
import com.example.compose.TaPagoTheme

@Composable
fun TabsScaffold() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) { padding ->
        HomeScreen(
            modifier = Modifier
                .padding(padding)
        )
    }
}

@Preview
@Composable
private fun TabsScaffoldPreview() {
    TaPagoTheme(
        dynamicColor = false
    ) {
        TabsScaffold()
    }
}