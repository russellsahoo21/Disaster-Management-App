package com.example.disastermanagementapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.disastermanagementapp.ui.screens.assistant.RoadsideAssistantScreen
import com.example.disastermanagementapp.ui.screens.auth.AuthScreen
import com.example.disastermanagementapp.ui.screens.dashboard.CrashDetectionScreen
import com.example.disastermanagementapp.ui.screens.dashboard.DashboardScreen
import com.example.disastermanagementapp.ui.screens.dashboard.DetectionScreen
import com.example.disastermanagementapp.ui.screens.onboarding.WelcomeScreen
import com.example.disastermanagementapp.ui.screens.settings.SettingsScreen

@Composable
fun AppNavGraph() {
    // The master engine controlling all page visibility
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {

        // 1. Welcome Splash Screen
        composable("welcome") {
            WelcomeScreen(
                onGetStartedClick = { navController.navigate("auth") },
                onLoginClick = { navController.navigate("auth") }
            )
        }

        // 2. Authentication Screen
        composable("auth") {
            AuthScreen(
                onSignInClick = { navController.navigate("dashboard") },
                onCreateAccountClick = { navController.navigate("dashboard") },
                onForgotPasswordClick = { }
            )
        }

        // 3. Main Home Dashboard
        composable("dashboard") {
            DashboardScreen(navController = navController)
        }

        // 4. Live AI Vision Detection Screen
        composable("detection") {
            DetectionScreen(navController = navController)
        }

        // 5. AI Roadside Assistant Chat Screen
        composable("assistant") {
            RoadsideAssistantScreen(navController = navController)
        }

        // 6. Profile & App Settings Screen
        composable("settings") {
            SettingsScreen(navController = navController)
        }

        // 7. Crash Countdown Overlay (Triggered during anomalies)
        composable("crash") {
            CrashDetectionScreen(
                onCancelAlert = { navController.popBackStack() },
                onCallEmergencyNow = { navController.navigate("assistant") }
            )
        }
    }
}