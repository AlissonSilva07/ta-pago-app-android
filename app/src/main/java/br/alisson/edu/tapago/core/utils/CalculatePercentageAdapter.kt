package br.alisson.edu.tapago.core.utils

fun calculatePercentage(current: Int, total: Int): Int {
    return if (total > 0) {
        ((current.toFloat() / total.toFloat()) * 100).toInt()
    } else {
        0
    }
}