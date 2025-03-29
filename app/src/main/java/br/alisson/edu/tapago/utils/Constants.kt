package br.alisson.edu.tapago.utils

import br.alisson.edu.tapago.presentation.tabs.pay.CategoryType

object Constants {
    const val BASE_URL = "http://10.0.2.2:3333/"
    val categories = listOf(
        CategoryType(1, "Alimentação"),
        CategoryType(2, "Transporte"),
        CategoryType(3, "Educação"),
        CategoryType(4, "Lazer"),
        CategoryType(5, "Saúde"),
        CategoryType(6, "Outros"),
    )
}