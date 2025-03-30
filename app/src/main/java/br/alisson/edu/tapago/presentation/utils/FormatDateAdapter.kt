package br.alisson.edu.tapago.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateAdapter(dateString: String): String {
    val instant = Instant.parse(dateString)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM", java.util.Locale("pt", "BR"))
    return localDate.format(formatter)
}