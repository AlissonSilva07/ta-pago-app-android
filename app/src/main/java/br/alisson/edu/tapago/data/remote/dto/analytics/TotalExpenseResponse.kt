package br.alisson.edu.tapago.data.remote.dto.analytics

import android.os.Build
import androidx.annotation.RequiresApi
import br.alisson.edu.tapago.domain.model.TotalExpense

data class TotalExpenseResponse(
    val month: String,
    val total: Int
)

@RequiresApi(Build.VERSION_CODES.O)
fun TotalExpenseResponse.toDomainModel(): TotalExpense {

    return TotalExpense(
        month = this.month,
        total = this.total
    )
}