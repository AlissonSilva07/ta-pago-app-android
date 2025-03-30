package br.alisson.edu.tapago.presentation.ui.signup

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.presentation.components.ButtonVariant
import br.alisson.edu.tapago.presentation.components.CustomButton
import br.alisson.edu.tapago.presentation.components.CustomTextField
import br.alisson.edu.tapago.presentation.components.PhotoSelectorView
import br.alisson.edu.tapago.presentation.components.TextFieldType
import br.alisson.edu.tapago.data.utils.NetworkResult
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import br.alisson.edu.tapago.presentation.ui.theme.TaPagoTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navigateBack: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val signUpResponse by viewModel.signUpResponse.collectAsState()
    val signUpState by viewModel.state.collectAsState()

    LaunchedEffect(signUpResponse) {
        when (signUpResponse) {
            is NetworkResult.Success -> {
                Toast.makeText(context, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            }
            is NetworkResult.Error -> {
                Toast.makeText(context, (signUpResponse as NetworkResult.Error).msg, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    scrolledContainerColor = MaterialTheme.colorScheme.onSurface
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cadastro",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                        content = {
                            Icon(
                                imageVector = Lucide.ChevronLeft,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                },
                actions = {
                    Icon(
                        imageVector = Lucide.ChevronLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
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
                        text = "Boas-vindas ao Tá Pago! 😉",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Entre com os dados abaixo para concluir o seu cadastro.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }

                var selectedImageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }

                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        selectedImageUri = uri
                        uri?.let {
                            val file = uriToFile(context, it)
                            viewModel.onEvent(SignUpEvent.UpdateProfilePicture(file))
                        }
                    }
                )

                PhotoSelectorView(
                    selectedImageUri,
                    singlePhotoPickerLauncher
                )

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Digite seu nome:",
                    value = signUpState.name,
                    onValueChange = { newValue ->
                        viewModel.onEvent(SignUpEvent.UpdateName(newValue))
                    },
                    error = signUpState.nameError
                )

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Digite seu e-mail:",
                    value = signUpState.email,
                    onValueChange = { newValue ->
                        viewModel.onEvent(SignUpEvent.UpdateEmail(newValue))
                    },
                    error = signUpState.emailError
                )

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Digite uma senha forte:",
                    value = signUpState.password,
                    onValueChange = { newValue ->
                        viewModel.onEvent(SignUpEvent.UpdatePassword(newValue))
                    },
                    error = signUpState.passwordError,
                    type = TextFieldType.PASSWORD
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(
                    title = "Cadastrar",
                    onClick = {
                        viewModel.onEvent(
                            SignUpEvent.SignUp(
                                name = signUpState.name,
                                email = signUpState.email,
                                password = signUpState.password,
                                profilePicture = signUpState.profilePicture
                            )
                        )
                    },
                    variant = ButtonVariant.DEFAULT,
                    disabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Já possui cadastro? Faça login para continuar.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable {
                        navigateToLogin()
                    }
                )
            }
        }
    }
}

fun uriToFile(context: Context, uri: Uri): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val tempFile = File.createTempFile("profile_pic", ".jpg", context.cacheDir)
    inputStream.use { input -> tempFile.outputStream().use { output -> input.copyTo(output) } }
    return tempFile
}

@Preview(device = Devices.PIXEL_6)
@Composable
fun SignupScreenPreview() {
    TaPagoTheme(darkTheme = false, dynamicColor = false) {
        SignupScreen()
    }
}