package com.example.myapp.di

import android.content.Context
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.Settings
import com.example.myapp.ui.screen.auth.AuthViewModel
import com.example.myapp.ui.screen.setup.SetupViewModel
import com.example.myapp.ui.screen.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // SharedPreferences ベースの Settings 実装を提供
    single<Settings> {
        SharedPreferencesSettings(
            androidApplication().getSharedPreferences(
                "myapp_prefs",
                Context.MODE_PRIVATE
            )
        )
    }

    // ViewModels
    viewModel { SplashViewModel() }
    viewModel { AuthViewModel() }
    viewModel { SetupViewModel() }
}
