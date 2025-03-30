package br.alisson.edu.tapago.data.remote.dto.expenses

import android.os.Build
import androidx.annotation.RequiresApi
import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.presentation.utils.formatDateAdapter

data class ExpenseResponse(
    val id: String,
    val title: String,
    val description: String,
    val amount: Double,
    val category: String,
    val isPaid: Boolean,
    val dueDate: String,
    val userId: String,
    val createdAt: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun ExpenseResponse.toDomainModel(): Expense {
    val formattedCreatedAt = formatDateAdapter(this.createdAt)
    val formattedDueDate = formatDateAdapter(this.dueDate)

    return Expense(
        createdAt = formattedCreatedAt,
        id = this.id,
        title = this.title,
        description = this.description,
        amount = this.amount.toInt(),
        category = this.category,
        isPaid = this.isPaid,
        dueDate = formattedDueDate,
        userId = this.userId
    )
}


