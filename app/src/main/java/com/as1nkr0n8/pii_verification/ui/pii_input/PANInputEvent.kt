package com.as1nkr0n8.pii_verification.ui.pii_input

import androidx.annotation.StringRes
import com.as1nkr0n8.pii_verification.R

enum class PANInputEvent(@StringRes val resId: Int) {
    NONE(0), SUCCESS(0), EMPTY(R.string.field_empty_error), INVALID_INPUT(R.string.field_invalid_error)
}

enum class DOBInputEvent(@StringRes val resId: Int) {
    NONE(0),
    SUCCESS(0),
    EMPTY(R.string.field_empty_error),
    INVALID_INPUT(R.string.field_invalid_error)
}