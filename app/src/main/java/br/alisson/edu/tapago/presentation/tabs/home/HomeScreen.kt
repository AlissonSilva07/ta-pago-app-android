package br.alisson.edu.tapago.presentation.tabs.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.R
import br.alisson.edu.tapago.presentation.tabs.home.components.AcessoRapidoCard
import br.alisson.edu.tapago.presentation.tabs.home.components.HomeHeader
import com.composables.icons.lucide.CopyPlus
import com.composables.icons.lucide.DollarSign
import com.composables.icons.lucide.Lucide

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        HomeHeader("Usuário(a)", R.drawable.top_image)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Acesso rápido",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AcessoRapidoCard(
                    modifier = Modifier.weight(0.5f),
                    icon = Lucide.DollarSign,
                    title = "Pagar"
                )
                AcessoRapidoCard(
                    modifier = Modifier.weight(0.5f),
                    icon = Lucide.CopyPlus,
                    title = "Adicionar"
                )
            }
        }
    }
}