package com.example.myapp.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.settings.AppSettings
import com.example.myapp.domain.model.SetupStep
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class SplashDestination {
    data object Onboarding : SplashDestination()
    data object Auth : SplashDestination()
    data class ResumeSetup(val step: String) : SplashDestination()
    data object Loading : SplashDestination()
}

class SplashViewModel : ViewModel(), KoinComponent {

    private val appSettings: AppSettings by inject()

    private val _destination = MutableStateFlow<SplashDestination>(SplashDestination.Loading)
    val destination: StateFlow<SplashDestination> = _destination.asStateFlow()

    init {
        checkInitialDestination()
    }

    private fun checkInitialDestination() {
        viewModelScope.launch {
            delay(800) // スプラッシュ最低表示時間
            _destination.value = when {
                !appSettings.agreedToTerms -> SplashDestination.Onboarding
                appSettings.authToken == null -> SplashDestination.Auth
                appSettings.setupStep != SetupStep.COMPLETE ->
                    SplashDestination.ResumeSetup(appSettings.setupStep.name)
                else -> SplashDestination.ResumeSetup(SetupStep.COMPLETE.name)
            }
        }
    }
}
