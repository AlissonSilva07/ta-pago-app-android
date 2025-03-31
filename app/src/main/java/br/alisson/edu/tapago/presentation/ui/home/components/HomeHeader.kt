package br.alisson.edu.tapago.presentation.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.R
import coil.compose.AsyncImage

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    name: String? = null,
    avatar: ByteArray? = null
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Olá,",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = name ?: "Usuário(a)",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
        AsyncImage(
            model = avatar ?: R.drawable.logo_splash,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .clip(RoundedCornerShape(percent = 100))
        )
    }
}