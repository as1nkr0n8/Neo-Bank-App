package com.as1nkr0n8.pii_verification.ui.pii_input

import android.util.Log
import androidx.databinding.Observable
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

    private val panTextChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            Log.d("TAG", "panInput")
            isPANValid()
            updateNextState()
        }
    }
    private val dobTextChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            isBirthDateValid()
            updateNextState()
        }
    }

    init {
        panInputText.addOnPropertyChangedCallback(panTextChangedCallback)
        dayInputText.addOnPropertyChangedCallback(dobTextChangedCallback)
        monthInputText.addOnPropertyChangedCallback(dobTextChangedCallback)
        yearInputText.addOnPropertyChangedCallback(dobTextChangedCallback)
    }

    private fun isBirthDateValid(): Boolean {
        return when {
            dayInputText.get().isNullOrBlank() -> {
                dayEvent.postValue(DOBInputEvent.EMPTY)
                false
            }
            monthInputText.get().isNullOrBlank() -> {
                dayEvent.postValue(DOBInputEvent.NONE)
                monthEvent.postValue(DOBInputEvent.EMPTY)
                false
            }
            yearInputText.get().isNullOrBlank() -> {
                monthEvent.postValue(DOBInputEvent.NONE)
                yearEvent.postValue(DOBInputEvent.EMPTY)
                false
            }
            BirthDate(
                dayInputText.get()!!.toInt(),
                monthInputText.get()!!.toInt(),
                yearInputText.get()!!.toInt()
            ).isValid()
                .not() -> {
                dayEvent.postValue(DOBInputEvent.INVALID_INPUT)
                monthEvent.postValue(DOBInputEvent.INVALID_INPUT)
                yearEvent.postValue(DOBInputEvent.INVALID_INPUT)
                false
            }
            else -> {
                dayEvent.postValue(DOBInputEvent.SUCCESS)
                monthEvent.postValue(DOBInputEvent.SUCCESS)
                yearEvent.postValue(DOBInputEvent.SUCCESS)
                true
            }
        }
    }

    private fun isPANValid(): Boolean {
        return when {
            panInputText.get().isNullOrBlank() -> {
                panEvent.postValue(PANInputEvent.EMPTY)
                false
            }
            PAN(panInputText.get()!!).isValid().not() -> {
                panEvent.postValue(PANInputEvent.INVALID_INPUT)
                false
            }
            else -> {
                panEvent.postValue(PANInputEvent.SUCCESS)
                true
            }
        }
    }

    fun updateNextState() {
        val bothValid = panEvent.value == PANInputEvent.SUCCESS
                && dayEvent.value == DOBInputEvent.SUCCESS
                && monthEvent.value == DOBInputEvent.SUCCESS
                && yearEvent.value == DOBInputEvent.SUCCESS
        isNextEnabled.postValue(bothValid)
    }

    fun onNextClicked() {
        nextClicked.postValue(true)
    }

    fun onNoPANClicked() {
        noPANClicked.postValue(true)
    }

    override fun onCleared() {
        super.onCleared()
        panInputText.removeOnPropertyChangedCallback(panTextChangedCallback)
        dayInputText.removeOnPropertyChangedCallback(dobTextChangedCallback)
        monthInputText.removeOnPropertyChangedCallback(dobTextChangedCallback)
        yearInputText.removeOnPropertyChangedCallback(dobTextChangedCallback)
    }
}