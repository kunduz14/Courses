package com.kunduzamatbekova.android.core.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDateToRussian(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))

    return if (date != null) {
        val formatted = outputFormat.format(date)
        val regex = Regex("""\d+\s([а-яёa-zA-Z])""")

        regex.replace(formatted) { matchResult ->
            val firstLetter = matchResult.groupValues[1]
            matchResult.value.replaceFirst(firstLetter, firstLetter.uppercase(Locale("ru")))
        }
    } else {
        ""
    }
}