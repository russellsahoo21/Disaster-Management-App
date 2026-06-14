package com.example.disastermanagementapp.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// --- Theme Tokens ---
private val PrimaryGreen = Color(0xFF006D36)
private val PrimaryContainer = Color(0xFF50C878)
private val AppBackground = Color(0xFFF9F9F9)
private val SurfaceContainerLow = Color(0xFFF3F3F3)
private val OnSurfaceCharcoal = Color(0xFF1A1C1C)
private val OnSurfaceVariantGrey = Color(0xFF3E4A3F)
private val OutlineVariant = Color(0xFFBDCABC)
private val AlertRed = Color(0xFFBA1A1A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    // State for toggles
    var autoDispatchEnabled by remember { mutableStateOf(true) }
    var watchSyncEnabled by remember { mutableStateOf(true) }
    var routeMonitoringEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { SettingsTopBar() },
        bottomBar = { SettingsBottomNav(navController = navController) },
        containerColor = AppBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // --- Profile Header ---
            ProfileSection()

            // --- Group 1: Emergency Configuration ---
            SettingGroup(title = "EMERGENCY CONFIGURATION") {
                SettingClickableItem(
                    title = "SOS Contacts",
                    subtitle = "2 Contacts added",
                    onClick = { /* Navigate to contacts */ }
                )
                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.2f))
                SettingToggleItem(
                    title = "Auto-Dispatch Emergency Services",
                    checked = autoDispatchEnabled,
                    onCheckedChange = { autoDispatchEnabled = it }
                )
            }

            // --- Group 2: Hardware & Sensors ---
            SettingGroup(title = "HARDWARE & SENSORS") {
                SettingClickableItem(
                    title = "Galaxy Phone IMU Sensitivity",
                    subtitle = "Medium (4.5g trigger)",
                    onClick = { /* Navigate to IMU settings */ }
                )
                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.2f))
                SettingToggleItem(
                    title = "Galaxy Watch Sync",
                    subtitle = "Connected",
                    isOnlineStatus = true,
                    checked = watchSyncEnabled,
                    onCheckedChange = { watchSyncEnabled = it }
                )
            }

            // --- Group 3: AI Assistant ---
            SettingGroup(title = "AI ASSISTANT") {
                SettingClickableItem(
                    title = "Voice Preferences",
                    subtitle = "Calm Female Voice",
                    onClick = { /* Navigate to voice settings */ }
                )
                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.2f))
                SettingToggleItem(
                    title = "Proactive Route Monitoring",
                    checked = routeMonitoringEnabled,
                    onCheckedChange = { routeMonitoringEnabled = it }
                )
            }

            // --- Danger Zone ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    onClick = { /* Handle Logout */ },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, AlertRed),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Version 2.4.1 (Stable Build)",
                    fontSize = 12.sp,
                    color = OutlineVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ProfileSection() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar Placeholder with Status Badge
            Box(modifier = Modifier.size(64.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(OutlineVariant.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = OutlineVariant)
                }
                // Green check badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(20.dp)
                        .background(Color.White, CircleShape)
                        .padding(2.dp)
                        .background(PrimaryGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text("Alex Driver", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceCharcoal)
                Text("alex.d@safedrive.ai", fontSize = 14.sp, color = OnSurfaceVariantGrey)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Edit Profile",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryGreen,
                    modifier = Modifier.clickable { /* Handle edit */ }
                )
            }
        }
    }
}

@Composable
private fun SettingGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerLow)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurfaceVariantGrey,
                    letterSpacing = 1.sp
                )
            }
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))
            Column(content = content)
        }
    }
}

@Composable
private fun SettingClickableItem(title: String, subtitle: String? = null, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = OnSurfaceCharcoal)
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 14.sp, color = OnSurfaceVariantGrey)
            }
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = OutlineVariant)
    }
}

@Composable
private fun SettingToggleItem(
    title: String,
    subtitle: String? = null,
    isOnlineStatus: Boolean = false,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = OnSurfaceCharcoal)
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isOnlineStatus && checked) {
                        Box(modifier = Modifier.size(8.dp).background(PrimaryGreen, CircleShape))
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Text(text = subtitle, fontSize = 14.sp, color = OnSurfaceVariantGrey)
                }
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryContainer,
                uncheckedThumbColor = OutlineVariant,
                uncheckedTrackColor = SurfaceContainerLow,
                uncheckedBorderColor = OutlineVariant
            )
        )
    }
}

@Composable
private fun SettingsTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Handle Back */ }, modifier = Modifier.size(36.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = OnSurfaceCharcoal)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Settings",
                color = PrimaryGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(PrimaryContainer.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Shield, contentDescription = null, tint = PrimaryGreen)
        }
    }
}

@Composable
private fun SettingsBottomNav(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = OnSurfaceVariantGrey,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("dashboard") }, // <-- Route added
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
            label = { Text("Dashboard") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("detection") }, // 👈 Update this line
            icon = { Icon(Icons.Outlined.Visibility, contentDescription = "Detection") },
            label = { Text("Detection") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("assistant") }, // <-- Route added
            icon = { Icon(Icons.Outlined.MedicalServices, contentDescription = "Assistance") },
            label = { Text("Assistance") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("settings") }, // <-- Route added
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                selectedTextColor = PrimaryGreen,
                indicatorColor = PrimaryContainer.copy(alpha = 0.2f)
            )
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}