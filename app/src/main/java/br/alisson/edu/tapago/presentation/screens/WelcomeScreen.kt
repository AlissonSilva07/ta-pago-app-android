package br.alisson.edu.tapago.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.R
import br.alisson.edu.tapago.presentation.components.ButtonVariant
import br.alisson.edu.tapago.presentation.components.CustomButton

@Composable
fun WelcomeScreen(navigateToLogin: () -> Unit) {
    Surface (
        modifier = Modifier.windowInsetsPadding(insets = WindowInsets.systemBars),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.frame),
                    contentDescription = null,
                    modifier = Modifier
                        .height(22.dp),
                )
                Text(
                    text = "Reúna suas contas em um lugar só.",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Depois, é só compartilhar com a galera.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomButton(
                        title = "Login",
                        onClick = navigateToLogin,
                        variant = ButtonVariant.DEFAULT,
                        disabled = false,
                        modifier = Modifier.weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    CustomButton(
                        title = "Cadastro",
                        onClick = {},
                        variant = ButtonVariant.MUTED,
                        disabled = false,
                        modifier = Modifier.weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}