package com.example.myapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.ui.screen.auth.AuthScreen
import com.example.myapp.ui.screen.onboarding.OnboardingScreen
import com.example.myapp.ui.screen.setup.ExtraSetupScreen
import com.example.myapp.ui.screen.setup.LocationSetupScreen
import com.example.myapp.ui.screen.setup.ProfileSetupScreen
import com.example.myapp.ui.screen.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { this.inclusive = true }
                    }
                },
                onNavigateToAuth = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Splash.route) { this.inclusive = true }
                    }
                },
                onNavigateToSetup = { step ->
                    val route = when (step) {
                        "PROFILE" -> Screen.ProfileSetup.route
                        "LOCATION" -> Screen.LocationSetup.route
                        "EXTRA" -> Screen.ExtraSetup.route
                        else -> Screen.Home.route
                    }
                    navController.navigate(route) {
                        popUpTo(Screen.Splash.route) { this.inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onAgreed = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Onboarding.route) { this.inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Auth.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(Screen.ProfileSetup.route) {
                        popUpTo(Screen.Auth.route) { this.inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ProfileSetup.route) {
            ProfileSetupScreen(
                onNext = {
                    navController.navigate(Screen.LocationSetup.route)
                }
            )
        }

        composable(Screen.LocationSetup.route) {
            LocationSetupScreen(
                onNext = {
                    navController.navigate(Screen.ExtraSetup.route)
                }
            )
        }

        composable(Screen.ExtraSetup.route) {
            ExtraSetupScreen(
                onComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { this.inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            // TODO: メイン画面を実装する
            androidx.compose.material3.Text("ホーム画面（実装予定）")
        }
    }
}