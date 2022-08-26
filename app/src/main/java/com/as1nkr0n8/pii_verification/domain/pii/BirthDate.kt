package com.as1nkr0n8.pii_verification.domain.pii

import java.time.DateTimeException
import java.time.LocalDate

data class BirthDate(val day: Int, val month: Int, val year: Int) {
    companion object {
        fun isDayValid(day: Int): Boolean {
            return when {
                day < 1 || day > 31 -> false
                else -> true
            }
        }
        fun isMonthValid(month: Int): Boolean {
            return when {
                month < 1 || month > 12 -> false
                else -> true
            }
        }
        fun isYearValid(year: Int): Boolean {
            val now = LocalDate.now()
            return when {
                year < now.minusYears(100).year || year > now.year -> false
                else -> true
            }
        }
    }
    fun isValid(): Boolean {
        val date: LocalDate
        try {
            date = LocalDate.of(year, month, day)
        } catch (e: DateTimeException) {
            return false
        }
        val today = LocalDate.now()
        return !(date.isEqual(today) || date.isAfter(today))
    }
}
