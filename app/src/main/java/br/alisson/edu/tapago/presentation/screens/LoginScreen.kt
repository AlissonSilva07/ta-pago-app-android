package br.alisson.edu.tapago.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.presentation.components.ButtonVariant
import br.alisson.edu.tapago.presentation.components.CustomButton
import br.alisson.edu.tapago.presentation.components.CustomTextField
import br.alisson.edu.tapago.presentation.components.TextFieldType
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.example.compose.TaPagoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.surface,
                    actionIconContentColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Lucide.ChevronLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                },
                modifier = Modifier.windowInsetsPadding(WindowInsets(left = 8.dp, right = 8.dp))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "Olá novamente! 😁",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Entre com suas credenciais para continuar.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.inverseSurface,
                    )
                }

                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "E-mail:",
                    value = email,
                    onValueChange = {
                        email = it
                    }
                )

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Senha:",
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    type = TextFieldType.PASSWORD
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(
                    title = "Entrar",
                    onClick = {},
                    variant = ButtonVariant.DEFAULT,
                    disabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Não tem cadastro? Faça login para continuar.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_6)
@Composable
fun LoginScreenPreview() {
    TaPagoTheme(darkTheme = false, dynamicColor = false) {
        LoginScreen()
    }
}