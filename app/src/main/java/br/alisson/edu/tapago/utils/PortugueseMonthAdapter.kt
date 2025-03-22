package br.alisson.edu.tapago.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getMonthInPortuguese(dateString: String): String {
    val formattedDateString = "$dateString-01"
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(formattedDateString, formatter)

    val monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale("pt", "BR"))
    return date.format(monthFormatter)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}