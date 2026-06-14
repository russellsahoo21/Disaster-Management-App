package com.example.disastermanagementapp.ui.screens.dashboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Define Color Scheme matching your design system constants
private val PrimaryGreen = Color(0xFF006D36)
private val PrimaryContainerGreen = Color(0xFF50C878)
private val AppBackground = Color(0xFFF9F9F9)
private val OnSurfaceCharcoal = Color(0xFF1A1C1C)
private val OnSurfaceVariantGrey = Color(0xFF3E4A3F)
private val OutlineVariant = Color(0xFFBDCABC)
private val AlertAmber = Color(0xFFF59E0B)
private val InfoBlue = Color(0xFF2196F3)
private val ErrorCritical = Color(0xFFBA1A1A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectionScreen(navController: NavController) {
    Scaffold(
        topBar = { DetectionTopBar() },
        bottomBar = { DetectionBottomNav(navController = navController) },
        containerColor = AppBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // 1. Live Dashcam / AI Vision Hero Feed
            item { LiveDashcamFeed() }

            // 2. Real-Time Telemetry Row
            item { TelemetryRow() }

            // 3. Event Log / Recent Detections List
            item { RecentDetectionsLog() }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun DetectionTopBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AppBackground,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Detection",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
            }

            // Pulsing "Sensors Active" Pill
            val infiniteTransition = rememberInfiniteTransition(label = "pulse")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0.4f,
                animationSpec = infiniteRepeatable(
                    animation = twistTwiceSpec(),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "alpha"
            )

            Row(
                modifier = Modifier
                    .background(PrimaryContainerGreen.copy(alpha = 0.15f), CircleShape)
                    .border(1.dp, OutlineVariant.copy(alpha = 0.3f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen.copy(alpha = alpha))
                )
                Text(
                    text = "Sensors Active",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryGreen
                )
            }
        }
    }
}

@Composable
private fun LiveDashcamFeed() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE2E2E2)) // Default UI structural placeholder grey
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
    ) {
        // AI Tracking Simulation Graphic Lines
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                )
        )

        // Live Badge Overlay
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .background(OnSurfaceCharcoal.copy(alpha = 0.6f), CircleShape)
                .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(ErrorCritical)
            )
            Text(
                text = "LIVE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.5.sp
            )
        }

        // Bottom text overlay
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "AI Vision & Lane Tracking Active",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun TelemetryRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Speedometer Card
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Current Speed", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "65",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                    Text(
                        text = " MPH",
                        fontSize = 12.sp,
                        color = OnSurfaceVariantGrey,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 0.65f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape),
                    color = PrimaryContainerGreen,
                    trackColor = Color(0xFFEEEEEE),
                )
            }
        }

        // Following Distance Card
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Following Distance", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "2.4",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                    Text(
                        text = " s",
                        fontSize = 12.sp,
                        color = OnSurfaceVariantGrey,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PrimaryGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(text = "Safe", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = PrimaryGreen)
                }
            }
        }
    }
}

@Composable
private fun RecentDetectionsLog() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Recent Detections",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurfaceCharcoal
                )
            }
            HorizontalDivider(color = Color(0xFFEEEEEE))

            DetectionLogItem(
                title = "Lane Drift Corrected",
                time = "Just now",
                icon = Icons.Default.Warning,
                iconColor = AlertAmber,
                bgColor = AlertAmber.copy(alpha = 0.1f)
            )
            HorizontalDivider(color = Color(0xFFEEEEEE))

            DetectionLogItem(
                title = "Pedestrian Detected",
                time = "2 mins ago",
                icon = Icons.Default.Info,
                iconColor = InfoBlue,
                bgColor = InfoBlue.copy(alpha = 0.1f)
            )
            HorizontalDivider(color = Color(0xFFEEEEEE))

            DetectionLogItem(
                title = "Speed Limit Changed (55 mph)",
                time = "15 mins ago",
                icon = Icons.Default.CheckCircle,
                iconColor = PrimaryGreen,
                bgColor = PrimaryContainerGreen.copy(alpha = 0.15f)
            )
        }
    }
}

@Composable
private fun DetectionLogItem(
    title: String,
    time: String,
    icon: ImageVector,
    iconColor: Color,
    bgColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceCharcoal)
                Text(text = time, fontSize = 12.sp, color = OnSurfaceVariantGrey)
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = OnSurfaceVariantGrey.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun DetectionBottomNav(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = OnSurfaceVariantGrey,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("dashboard") },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
            label = { Text("Dashboard") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("detection") }, // 👈 Update this line
            icon = { Icon(Icons.Default.Visibility, contentDescription = "Detection") },
            label = { Text("Detection") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                selectedTextColor = PrimaryGreen,
                indicatorColor = PrimaryContainerGreen.copy(alpha = 0.15f)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("assistant") },
            icon = { Icon(Icons.Outlined.MedicalServices, contentDescription = "Assistance") },
            label = { Text("Assistance") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("settings") },
            icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}

private fun twistTwiceSpec(): TweenSpec<Float> = tween(
    durationMillis = 1000,
    easing = LinearEasing
)

@Preview(showBackground = true)
@Composable
fun DetectionScreenPreview() {
    DetectionScreen(navController = rememberNavController())
}