package com.example.myapp.ui.screen.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.example.myapp.data.settings.AppSettings
import com.example.myapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel(), KoinComponent {

    private val userRepository: UserRepository by inject()
    private val appSettings: AppSettings by inject()

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
                    .build()
                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(signInWithGoogleOption)
                    .build()
                val result = CredentialManager.create(context).getCredential(context, request)
                val credential = result.credential
                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    handleAuthResult(googleCredential.idToken, "google")
                } else {
                    _state.value = AuthState.Error("予期しない認証情報の種類です")
                }
            } catch (e: GetCredentialCancellationException) {
                _state.value = AuthState.Idle
            } catch (e: NoCredentialException) {
                _state.value = AuthState.Error("Googleアカウントが見つかりませんでした")
            } catch (e: GetCredentialException) {
                _state.value = AuthState.Error("ログインに失敗しました: ${e.message}")
            }
        }
    }

    fun signInWithApple(context: Context) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            // TODO: AppAuth で Apple Sign-In を実装
            _state.value = AuthState.Error("Apple Sign-In は実装中です")
        }
    }

    fun signInWithInstagram(context: Context) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            // TODO: AppAuth で Instagram OAuth 2.0 を実装
            _state.value = AuthState.Error("Instagram ログインは実装中です")
        }
    }

    private suspend fun handleAuthResult(idToken: String, provider: String) {
        userRepository.createUser(idToken, provider).fold(
            onSuccess = {
                appSettings.authToken = idToken
                _state.value = AuthState.Success
            },
            onFailure = { e ->
                _state.value = AuthState.Error(e.message ?: "ログインに失敗しました")
            }
        )
    }

    fun clearError() {
        _state.value = AuthState.Idle
    }

    companion object {
        // TODO: Firebase Console の google-services.json（client_type:3）の client_id に差し替える
        private const val WEB_CLIENT_ID = "YOUR_GOOGLE_WEB_CLIENT_ID"
    }
}
