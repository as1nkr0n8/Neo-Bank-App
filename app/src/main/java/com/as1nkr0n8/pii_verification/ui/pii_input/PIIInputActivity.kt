package com.as1nkr0n8.pii_verification.ui.pii_input

import android.os.Bundle
import android.text.InputFilter
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.as1nkr0n8.pii_verification.R
import com.as1nkr0n8.pii_verification.databinding.ActivityPiiinputBinding


class PIIInputActivity : AppCompatActivity() {
    private val viewModel: PIIInputViewModel by viewModels()
    private lateinit var binding: ActivityPiiinputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_piiinput)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onStart() {
        super.onStart()
        binding.panEditText.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(10))
        //PAN Input Events
        viewModel.panEvent.observe(this) { event ->
            when (event) {
                PANInputEvent.EMPTY, PANInputEvent.INVALID_INPUT -> {
                    binding.panTextField.error = getString(event.resId)
                }
                else -> {
                    binding.panTextField.error = null
                    if(event == PANInputEvent.SUCCESS) {
                        binding.panTextField.clearFocus()
                        binding.dayTextField.requestFocus()
                    }
                }
            }
        }
        //Day Input Events
        viewModel.dayEvent.observe(this) { event ->
            when (event) {
                DOBInputEvent.EMPTY, DOBInputEvent.INVALID_INPUT -> {
                    binding.dayTextField.error = getString(event.resId)
                }
                else -> {
                    binding.dayTextField.error = null
                }
            }
        }
        //Month Input Events
        viewModel.monthEvent.observe(this) { event ->
            when (event) {
                DOBInputEvent.EMPTY, DOBInputEvent.INVALID_INPUT -> {
                    binding.monthTextField.error = getString(event.resId)
                }
                else -> {
                    binding.monthTextField.error = null
                }
            }
        }
        //Year Input Events
        viewModel.yearEvent.observe(this) { event ->
            when (event) {
                DOBInputEvent.EMPTY, DOBInputEvent.INVALID_INPUT -> {
                    binding.yearTextField.error = getString(event.resId)
                }
                else -> {
                    binding.yearTextField.error = null
                }
            }
        }
        //Next clicks
        viewModel.nextClicked.observe(this) { clicked ->
            if (clicked) {
                Toast.makeText(
                    this,
                    R.string.details_submitted_text,
                    Toast.LENGTH_LONG
                ).show()
                viewModel.nextClicked.value = false
                finish()
            }
        }
        //No PAN clicks
        viewModel.noPANClicked.observe(this) { clicked ->
            if (clicked) {
                viewModel.noPANClicked.value = false
                finish()
            }
        }
    }
}