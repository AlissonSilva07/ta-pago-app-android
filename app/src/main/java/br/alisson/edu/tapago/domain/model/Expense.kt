package br.alisson.edu.tapago.domain.model

data class Expense(
    val amount: Int,
    val category: String,
    val createdAt: String,
    val description: String,
    val dueDate: String,
    val id: String,
    val isPaid: Boolean,
    val title: String,
    val userId: String
)