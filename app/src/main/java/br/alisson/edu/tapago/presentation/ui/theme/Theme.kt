package br.alisson.edu.tapago.presentation.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.AppTypography

@Immutable
data class ExtendedColorScheme(
    val orange: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primary,
    secondary = lighterGray,
    tertiary = cyan,
    surface = background,
    onSurface = textPrimary,
    inverseOnSurface = textSecondary,
    error = warning
)

private val darkScheme = darkColorScheme(

)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun TaPagoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {

  MaterialTheme(
    colorScheme = lightScheme,
    typography = AppTypography,
    content = content
  )
}

