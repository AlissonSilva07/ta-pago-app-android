package br.alisson.edu.tapago

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import br.alisson.edu.tapago.presentation.auth.login.LoginViewModel
import br.alisson.edu.tapago.presentation.navigation.AppNavHost
import br.alisson.edu.tapago.presentation.ui.theme.TaPagoTheme
import br.alisson.edu.tapago.presentation.ui.theme.background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = background.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(scrim = background.toArgb())
        )
        setContent {
            TaPagoTheme(darkTheme = false, dynamicColor = false) {
                AppNavHost(loginViewModel = loginViewModel)
            }
        }
    }
}

