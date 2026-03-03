package com.example.myapp.ui.screen.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.settings.AppSettings
import com.example.myapp.domain.model.SetupStep
import com.example.myapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class SetupUiState {
    data object Idle : SetupUiState()
    data object Loading : SetupUiState()
    data object Success : SetupUiState()
    data class Error(val message: String) : SetupUiState()
}

class SetupViewModel : ViewModel(), KoinComponent {

    private val appSettings: AppSettings by inject()
    private val userRepository: UserRepository by inject()

    private val _uiState = MutableStateFlow<SetupUiState>(SetupUiState.Idle)
    val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

    // プロフィール設定
    fun saveProfile(displayName: String) {
        viewModelScope.launch {
            _uiState.value = SetupUiState.Loading
            // TODO: PATCH /api/v1/users/me でユーザー名更新
            appSettings.setupStep = SetupStep.LOCATION
            _uiState.value = SetupUiState.Success
        }
    }

    // 位置情報設定（スキップ可能）
    fun saveLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _uiState.value = SetupUiState.Loading
            // TODO: 位置情報をサーバーへ送信
            appSettings.setupStep = SetupStep.EXTRA
            _uiState.value = SetupUiState.Success
        }
    }

    fun skipLocation() {
        appSettings.setupStep = SetupStep.EXTRA
        _uiState.value = SetupUiState.Success
    }

    // 追加セットアップ（TODO: アプリ固有の処理に差し替える）
    fun saveExtra() {
        viewModelScope.launch {
            _uiState.value = SetupUiState.Loading
            // TODO: アプリ固有のセットアップ処理を実装する
            appSettings.setupStep = SetupStep.COMPLETE
            _uiState.value = SetupUiState.Success
        }
    }

    fun skipExtra() {
        appSettings.setupStep = SetupStep.COMPLETE
        _uiState.value = SetupUiState.Success
    }

    fun clearState() {
        _uiState.value = SetupUiState.Idle
    }
}