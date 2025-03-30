package br.alisson.edu.tapago.presentation.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.data.utils.NetworkResult
import br.alisson.edu.tapago.presentation.components.ButtonVariant
import br.alisson.edu.tapago.presentation.components.CustomButton
import br.alisson.edu.tapago.presentation.components.CustomTextField
import br.alisson.edu.tapago.presentation.components.TextFieldType
import com.composables.icons.lucide.*
import br.alisson.edu.tapago.presentation.ui.theme.TaPagoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateBack: () -> Unit = {},
    navigateToSignup: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val loginResponse by viewModel.loginResponse.collectAsState()
    val loginState by viewModel.state.collectAsState()

    LaunchedEffect(loginResponse) {
        when (loginResponse) {
            is NetworkResult.Success -> {
                loginState.isLoading = false
                navigateToHome()
            }
            is NetworkResult.Error -> {
                loginState.isLoading = false
                Toast.makeText(
                    context,
                    (loginResponse as NetworkResult.Error).msg,
                    Toast.LENGTH_LONG
                ).show()
            }
            is NetworkResult.Loading -> {
                loginState.isLoading = true
            }
            NetworkResult.Idle -> {}
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Lucide.ChevronLeft,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 16.dp),
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
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Entre com suas credenciais para continuar.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "E-mail:",
                    value = loginState.email,
                    onValueChange = { newValue ->
                        viewModel.onEvent(LoginEvent.UpdateEmail(newValue))
                    },
                    error = loginState.emailError,
                )

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Senha:",
                    value = loginState.password,
                    onValueChange = { newValue ->
                        viewModel.onEvent(LoginEvent.UpdatePassword(newValue))
                    },
                    type = TextFieldType.PASSWORD,
                    error = loginState.passwordError,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(
                    title = "Entrar",
                    onClick = {
                        viewModel.onEvent(
                            LoginEvent.Login(
                                email = loginState.email,
                                password = loginState.password
                            )
                        )
                    },
                    variant = ButtonVariant.DEFAULT,
                    disabled = loginState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    icon = {
                        if (loginState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.surface,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                )
                Text(
                    text = "Não tem cadastro? Cadastre-se.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable { navigateToSignup() }
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
