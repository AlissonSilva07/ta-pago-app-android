package br.alisson.edu.tapago

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import br.alisson.edu.tapago.presentation.navigation.AppNavHost
import com.example.compose.TaPagoTheme
import com.example.compose.primaryContainerLight

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = primaryContainerLight.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(scrim = primaryContainerLight.toArgb())
        )
        setContent {
            TaPagoTheme(darkTheme = false, dynamicColor = false) {
                AppNavHost()
            }
        }
    }
}

