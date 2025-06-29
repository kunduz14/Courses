package com.kunduzamatbekova.android.feature_login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val MIN_PASSWORD_VALUE = 6

class LoginFragmentViewModel : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    private val _password = MutableStateFlow<String>("")

    private val _emailValidate = MutableStateFlow<Boolean>(false)
    val emailValidate: StateFlow<Boolean> = _emailValidate

    private val _passwordValidate = MutableStateFlow<Boolean>(false)
    val passwordValidate: StateFlow<Boolean> = _passwordValidate

    private val _isButtonEnabled = MutableStateFlow<Boolean>(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled.asStateFlow()

    fun performs(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> onEmailChanged(event.email)
            is LoginEvent.OnPasswordChanged -> onPasswordChanged(event.password)
        }
    }

    private fun onEmailChanged(email: String) {
        _email.value = email
        _isButtonEnabled.value = buttonEnabled()
    }

    private fun onPasswordChanged(password: String) {
        _password.value = password
        _isButtonEnabled.value = buttonEnabled()
    }

    private fun emailValidate(): Boolean {
        _emailValidate.value = _email.value.isValidEmail()
        return _emailValidate.value
    }

    private fun passwordValidate(): Boolean {
        _passwordValidate.value = _password.value.length >= MIN_PASSWORD_VALUE
        return _passwordValidate.value
    }

    private fun buttonEnabled(): Boolean {
        return  emailValidate() && passwordValidate()
    }
}

private fun String.isValidEmail() =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()