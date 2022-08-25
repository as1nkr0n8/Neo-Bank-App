package com.as1nkr0n8.pii_verification.domain.pii

import java.util.regex.Pattern

data class PAN(val value: String) {
    fun isValid(): Boolean {
        val panPattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]")
        return panPattern.matcher(value).matches()
    }
}
