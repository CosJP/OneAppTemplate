package com.example.myapp.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Auth : Screen("auth")
    data object ProfileSetup : Screen("setup/profile")
    data object LocationSetup : Screen("setup/location")
    data object ExtraSetup : Screen("setup/extra") // TODO: アプリ固有の追加セットアップ画面
    data object Home : Screen("home")
}
