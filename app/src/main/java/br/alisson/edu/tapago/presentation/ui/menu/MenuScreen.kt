package br.alisson.edu.tapago.presentation.ui.menu

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.alisson.edu.tapago.R
import br.alisson.edu.tapago.presentation.components.ButtonVariant
import br.alisson.edu.tapago.presentation.components.CustomButton
import br.alisson.edu.tapago.presentation.ui.user.UserEvent
import br.alisson.edu.tapago.presentation.ui.user.UserViewModel
import coil.compose.AsyncImage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userState = userViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.onEvent(UserEvent.GetData)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = userState.value.userData?.profilePicture ?: R.drawable.logo_splash,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(percent = 100))
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = userState.value.userData?.name ?: "Usuário(a)",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Ativo(a) desde: ${userState.value.userData?.createdAt}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomButton(
                title = "Apagar todos os dados",
                onClick = {},
                variant = ButtonVariant.DESTRUCTIVE,
                disabled = false,
                modifier = Modifier.fillMaxWidth(),
                icon = null
            )
            CustomButton(
                title = "Sair do App",
                onClick = {
                    (context as? Activity)?.let {
                        viewModel.logOut(it)
                    }
                },
                variant = ButtonVariant.MUTED,
                disabled = false,
                modifier = Modifier.fillMaxWidth(),
                icon = null
            )
        }

    }
}
