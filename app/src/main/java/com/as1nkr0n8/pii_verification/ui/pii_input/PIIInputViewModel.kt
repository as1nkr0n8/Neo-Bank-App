package com.as1nkr0n8.pii_verification.ui.pii_input

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.as1nkr0n8.pii_verification.domain.pii.BirthDate
import com.as1nkr0n8.pii_verification.domain.pii.PAN


class PIIInputViewModel : ViewModel() {
    val panInputText = ObservableField("")
    val panEvent = MutableLiveData(PANInputEvent.NONE)

    val dayInputText = ObservableField<String>()
    val monthInputText = ObservableField<String>()
    val yearInputText = ObservableField<String>()
    val dayEvent = MutableLiveData(DOBInputEvent.NONE)
    val monthEvent = MutableLiveData(DOBInputEvent.NONE)
    val yearEvent = MutableLiveData(DOBInputEvent.NONE)

    val isNextEnabled = MutableLiveData(false)
    val nextClicked = MutableLiveData(false)
    val noPANClicked = MutableLiveData(false)

    fun isPANValid(): Boolean {
        val panText = panInputText.get()
        val isValid = when {
            panText.isNullOrBlank() -> {
                panEvent.value = PANInputEvent.EMPTY
                false
            }
            PAN(panText).isValid().not() -> {
                panEvent.value = PANInputEvent.INVALID_INPUT
                false
            }
            else -> {
                panEvent.value = PANInputEvent.SUCCESS
                true
            }
        }
        updateNextState()
        return isValid
    }

    fun isDayValid(): Boolean {
        val dayText = dayInputText.get()
        val isValid = when {
            dayText.isNullOrBlank() -> {
                dayEvent.value = DOBInputEvent.EMPTY
                false
            }
            BirthDate.isDayValid(dayText.toInt()).not() -> {
                dayEvent.value = DOBInputEvent.INVALID_INPUT
                false
            }
            else -> {
                dayEvent.value = DOBInputEvent.SUCCESS
                true
            }
        }
        isBirthDateValid()
        updateNextState()
        return isValid
    }

    fun isMonthValid(): Boolean {
        val monthText = monthInputText.get()
        val isValid = when {
            monthText.isNullOrBlank() -> {
                monthEvent.value = DOBInputEvent.EMPTY
                false
            }
            BirthDate.isMonthValid(monthText.toInt()).not() -> {
                monthEvent.value = DOBInputEvent.INVALID_INPUT
                false
            }
            else -> {
                monthEvent.value = DOBInputEvent.SUCCESS
                true
            }
        }
        isBirthDateValid()
        updateNextState()
        return isValid
    }

    fun isYearValid(): Boolean {
        val yearText = yearInputText.get()
        val isValid = when {
            yearText.isNullOrBlank() -> {
                yearEvent.value = DOBInputEvent.EMPTY
                false
            }
            BirthDate.isYearValid(yearText.toInt()).not() -> {
                yearEvent.value = DOBInputEvent.INVALID_INPUT
                false
            }
            else -> {
                yearEvent.value = DOBInputEvent.SUCCESS
                true
            }
        }
        isBirthDateValid()
        updateNextState()
        return isValid
    }

    private fun isBirthDateValid(): Boolean {
        return when {
            dayInputText.get().isNullOrBlank() || dayEvent.value?.isError() == true -> {
                false
            }
            monthInputText.get().isNullOrBlank() || monthEvent.value?.isError() == true -> {
                false
            }
            yearInputText.get().isNullOrBlank() || yearEvent.value?.isError() == true -> {
                false
            }
            BirthDate(
                dayInputText.get()!!.toInt(),
                monthInputText.get()!!.toInt(),
                yearInputText.get()!!.toInt()
            ).isValid()
                .not() -> {
                dayEvent.value = DOBInputEvent.INVALID_INPUT
                monthEvent.value = DOBInputEvent.INVALID_INPUT
                yearEvent.value = DOBInputEvent.INVALID_INPUT
                false
            }
            else -> {
                dayEvent.value = DOBInputEvent.SUCCESS
                monthEvent.value = DOBInputEvent.SUCCESS
                yearEvent.value = DOBInputEvent.SUCCESS
                true
            }
        }
    }

    private fun updateNextState() {
        val allValid = panEvent.value == PANInputEvent.SUCCESS
                && dayEvent.value == DOBInputEvent.SUCCESS
                && monthEvent.value == DOBInputEvent.SUCCESS
                && yearEvent.value == DOBInputEvent.SUCCESS
        isNextEnabled.value = allValid
    }

    fun onNextClicked() {
        nextClicked.value = true
    }

    fun onNoPANClicked() {
        noPANClicked.value = true
    }
}