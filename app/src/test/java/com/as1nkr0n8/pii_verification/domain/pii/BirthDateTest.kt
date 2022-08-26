package com.as1nkr0n8.pii_verification.domain.pii

import org.junit.Test

class BirthDateTest {
    @Test
    fun isDayValid() {
        var day = -1
        assert(BirthDate.isDayValid(day).not())
        day = 35
        assert(BirthDate.isDayValid(day).not())
        day = 5
        assert(BirthDate.isDayValid(day))
    }

    @Test
    fun isMonthValid() {
        var month = -1
        assert(BirthDate.isMonthValid(month).not())
        month = 15
        assert(BirthDate.isMonthValid(month).not())
        month = 8
        assert(BirthDate.isMonthValid(month))
    }

    @Test
    fun isYearValid() {
        var year = 250
        assert(BirthDate.isYearValid(year).not())
        year = 2500
        assert(BirthDate.isYearValid(year).not())
        year = 2005
        assert(BirthDate.isYearValid(year))

    }

    @Test
    fun isDOBValid() {
        val validBirthDate = BirthDate(1, 1, 2000)
        val invalidBirthDate = BirthDate(31, 2, 2000)
        assert(validBirthDate.isValid())
        assert(invalidBirthDate.isValid().not())
    }
}