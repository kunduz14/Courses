package com.kunduzamatbekova.android.feature_login

sealed interface LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent
    data class OnPasswordChanged(val password: String) : LoginEvent
}