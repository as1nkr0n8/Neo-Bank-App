package com.as1nkr0n8.pii_verification.domain.pii

import java.time.DateTimeException
import java.time.LocalDate

data class BirthDate(val day: Int, val month: Int, val year: Int) {
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
