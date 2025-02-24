package br.alisson.edu.tapago.presentation.home.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeHeader(
    name: String,
    avatar: Int
) {
    Row (
        modifier = Modifier
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.surface,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .clip(RoundedCornerShape(percent = 100))
        )
    }
}