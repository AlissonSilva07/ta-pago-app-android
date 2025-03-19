package br.alisson.edu.tapago.presentation.splash

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.R
import kotlinx.coroutines.time.delay
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SplashScreen(navigateToWelcome: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(Duration.ofMillis(2000)) // 2-second delay
        navigateToWelcome() // Navigate to the login screen after delay
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo_splash,
            ),
            contentDescription = null,
            modifier = Modifier.height(64.dp)
        )
    }
}