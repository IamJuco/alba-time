package com.juco.common

import java.text.NumberFormat
import java.util.Locale

fun formatWithComma(value: Any): String {
    return try {
        val number = when (value) {
            is Number -> value.toDouble()
            is String -> value.toDoubleOrNull() ?: return value
            else -> return value.toString()
        }
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
        formatter.format(number)
    } catch (e: Exception) {
        value.toString()
    }
}