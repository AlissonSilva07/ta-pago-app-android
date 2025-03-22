package br.alisson.edu.tapago.data.remote.dto.analytics

import android.os.Build
import androidx.annotation.RequiresApi
import br.alisson.edu.tapago.domain.model.MonthlyExpenseProgress

data class MonthlyExpenseProgressResponse(
    val current: Int,
    val total: Int
)

@RequiresApi(Build.VERSION_CODES.O)
fun MonthlyExpenseProgressResponse.toDomainModel(): MonthlyExpenseProgress {

    return MonthlyExpenseProgress(
        current = this.current,
        total = this.total
    )
}