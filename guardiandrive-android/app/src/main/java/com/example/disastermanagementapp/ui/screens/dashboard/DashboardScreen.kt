package com.example.disastermanagementapp.ui.screens.dashboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput

// --- Theme Tokens ---
private val PrimaryGreen = Color(0xFF006D36)
private val AppBackground = Color(0xFFF9F9F9)
private val CardBackground = Color(0xFFF2F6F9) // Soft blue-grey from screenshot
private val OnSurfaceCharcoal = Color(0xFF1A1C1C)
private val OnSurfaceVariantGrey = Color(0xFF3E4A3F)
private val OutlineVariant = Color(0xFFBDCABC)
private val SosRed = Color(0xFFBA1A1A)
private val ActivePillBg = Color(0xFFE2F5EA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        topBar = { DashboardTopBar() },
        bottomBar = { DashboardBottomNav(navController = navController) },
        containerColor = AppBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Hardware Telemetry Cards ---
            ImuTelemetryCard()
            BiometricsCard()

            // --- Pre-Flight Checklist ---
            SafetyChecklistCard()

            // --- Route Visual Card ---
            RouteCard()

            // --- SOS Action Section ---
            SosSection(navController = navController)
        }
    }
}

@Composable
private fun DashboardTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Logo",
                tint = PrimaryGreen,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "GuardianDrive",
                color = PrimaryGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = "System Checked",
            tint = PrimaryGreen,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun ImuTelemetryCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CardBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, OutlineVariant.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.PhoneAndroid, contentDescription = null, tint = PrimaryGreen)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Galaxy Phone IMU", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurfaceCharcoal)
                        Text("Real-time G-Force Analysis", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                    }
                }
                // Active Pill
                Box(
                    modifier = Modifier
                        .background(ActivePillBg, RoundedCornerShape(16.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("ACTIVE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text("Stability Score", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                    Text("98.4%", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }

                // Simulated Telemetry Graph Placeholder
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
                        val path = Path().apply {
                            moveTo(0f, size.height / 2)
                            lineTo(size.width * 0.3f, size.height / 2)
                            lineTo(size.width * 0.4f, size.height * 0.2f)
                            lineTo(size.width * 0.6f, size.height * 0.8f)
                            lineTo(size.width * 0.7f, size.height / 2)
                            lineTo(size.width, size.height / 2)
                        }
                        drawPath(path, PrimaryGreen.copy(alpha = 0.6f), style = Stroke(width = 3f))
                    }
                }
            }
        }
    }
}

@Composable
private fun BiometricsCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CardBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, OutlineVariant.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Watch Icon (Using generic device icon if specific watch unavailable)
                        Icon(Icons.Default.Watch, contentDescription = null, tint = PrimaryGreen)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Galaxy Watch Biometrics", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurfaceCharcoal)
                        Text("Driver Fatigue Monitor", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                    }
                }

                // Synced Pill with animating dot
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(ActivePillBg, RoundedCornerShape(16.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Box(modifier = Modifier.size(6.dp).background(PrimaryGreen, CircleShape))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("SYNCED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Heart Rate Box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text("Heart Rate", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("72", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceCharcoal)
                            Text(" BPM", fontSize = 12.sp, color = OnSurfaceCharcoal, modifier = Modifier.padding(bottom = 2.dp))
                        }
                    }
                }
                // Stress Level Box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text("Stress Level", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                        Text("Low", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceCharcoal)
                    }
                }
            }
        }
    }
}

@Composable
private fun SafetyChecklistCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pre-Flight Safety Check", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceCharcoal)
            Spacer(modifier = Modifier.height(12.dp))

            val checks = listOf("Seatbelt Fastened", "Tire Pressure Optimal", "Lane Assist Ready", "Weather Alert Scan")
            checks.forEach { checkItem ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(AppBackground, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(checkItem, fontSize = 14.sp, color = OnSurfaceCharcoal)
                }
            }
        }
    }
}

@Composable
private fun RouteCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray) // Fallback if image doesn't load
    ) {
        // NOTE: In the real app, use the Coil library for network images, e.g., AsyncImage(...)
        // Since we don't have the local image resource, we fake the gradient overlay.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("Current Route", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
            Text("Scenic Mountain Pass", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}

@Composable
private fun SosSection(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text("System Ready", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceCharcoal)
        Text("Your journey is being monitored by GuardianDrive AI", fontSize = 14.sp, color = OnSurfaceVariantGrey)

        Spacer(modifier = Modifier.height(24.dp))

        // SOS Button (Updated for Long-Press)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SosRed),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            // Triggers the emergency screen when held down
                            navController.navigate("crash")
                        }
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("*", fontSize = 48.sp, color = Color.White, modifier = Modifier.offset(y = (8).dp)) // Simulated asterisk icon
                Text("MANUAL SOS", fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, color = Color.White)
                Text("HOLD TO ACTIVATE", fontSize = 10.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Info, contentDescription = null, tint = OutlineVariant, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Accidental triggers can be cancelled within 5 seconds.", fontSize = 12.sp, color = OutlineVariant)
        }
    }
}

@Composable
private fun DashboardBottomNav(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = OnSurfaceVariantGrey,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("dashboard") },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                selectedTextColor = PrimaryGreen,
                indicatorColor = PrimaryGreen.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("detection") }, // 👈 Update this line
            icon = { Icon(Icons.Outlined.Visibility, contentDescription = "Detection") },
            label = { Text("Detection") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("assistant") }, // Jumps to Assistant!
            icon = { Icon(Icons.Outlined.MedicalServices, contentDescription = "Assistance") },
            label = { Text("Assistance") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("settings") }, // Jumps to Settings!
            icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    // Pass a dummy controller just for the preview
    DashboardScreen(navController = rememberNavController())
}