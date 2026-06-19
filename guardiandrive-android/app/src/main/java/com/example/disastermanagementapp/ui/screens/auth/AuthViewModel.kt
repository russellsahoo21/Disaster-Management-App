package com.example.disastermanagementapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disastermanagementapp.data.network.RetrofitInstance
import com.example.disastermanagementapp.data.network.AuthRequest
import com.example.disastermanagementapp.data.network.AuthResponse
import com.example.disastermanagementapp.data.network.GoogleTokenRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun login(request: AuthRequest) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(request)
                handleResponse(response)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Network error")
            }
        }
    }

    fun register(request: AuthRequest) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.register(request)
                handleResponse(response)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Network error")
            }
        }
    }

    fun onGoogleSignInResult(idToken: String?) {
        if (idToken == null) {
            _authState.value = AuthState.Error("Google Sign-In failed")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.googleAuth(GoogleTokenRequest(idToken))
                handleResponse(response)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Network error")
            }
        }
    }

    private fun handleResponse(response: retrofit2.Response<AuthResponse>) {
        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!.token
            // TODO: Save token to DataStore
            _authState.value = AuthState.Success(token)
        } else {
            val errorMsg = when (response.code()) {
                401 -> "Invalid credentials"
                403 -> "Access denied"
                400 -> "Bad request. Please check your inputs."
                else -> "Authentication failed: ${response.code()}"
            }
            _authState.value = AuthState.Error(errorMsg)
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
