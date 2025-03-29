package br.alisson.edu.tapago.data.remote.dto.expenses

data class PostExpenseRequest(
    val amount: Int,
    val category: String,
    val description: String,
    val dueDate: String,
    val isPaid: Boolean,
    val title: String
)