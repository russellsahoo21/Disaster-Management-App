package com.example.disastermanagementapp.ui.screens.assistant

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// --- Theme Tokens ---
private val PrimaryGreen = Color(0xFF006D36)
private val PrimaryContainer = Color(0xFF50C878)
private val AppBackground = Color(0xFFF9F9F9)
private val SurfaceContainerLow = Color(0xFFF3F3F3)
private val OnSurfaceCharcoal = Color(0xFF1A1C1C)
private val OnSurfaceVariantGrey = Color(0xFF3E4A3F)
private val OutlineVariant = Color(0xFFBDCABC)

// Severity & Alert Tokens
private val ErrorContainer = Color(0xFFFFDAD6)
private val OnErrorContainer = Color(0xFF93000A)
private val SeverityBarColor = Color(0xFFA43C12)
private val AlertRed = Color(0xFFBA1A1A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoadsideAssistantScreen(navController: NavController) {
    Scaffold(
        topBar = { AssistantTopBar() },
        bottomBar = { AssistantBottomNav(navController = navController) },
        containerColor = AppBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --- Chat Feed Area ---
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    AiMessageBubble(
                        message = "Hello! I've detected a mechanical fault in your vehicle. Are you and your passengers safe?",
                        timestamp = "Guardian AI • 10:24 AM"
                    )
                }

                item {
                    UserMessageBubble(
                        message = "We're safe, but the engine started smoking and we're pulled over on the I-95 shoulder.",
                        timestamp = "You • 10:25 AM"
                    )
                }

                item {
                    SeverityInsightCard()
                }

                item {
                    TrackingMessageBubble(timestamp = "Guardian AI • 10:26 AM")
                }
            }

            // --- Bottom Input Area ---
            ChatInputArea()
        }
    }
}

@Composable
private fun AiMessageBubble(message: String, timestamp: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp),
            color = SurfaceContainerLow,
            border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Text(
                text = message,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = OnSurfaceCharcoal,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = timestamp,
            fontSize = 12.sp,
            color = OutlineVariant,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun UserMessageBubble(message: String, timestamp: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomEnd = 16.dp, bottomStart = 16.dp),
            color = PrimaryGreen,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Text(
                text = message,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = timestamp,
            fontSize = 12.sp,
            color = OutlineVariant,
            modifier = Modifier.padding(end = 4.dp)
        )
    }
}

@Composable
private fun SeverityInsightCard() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Analytics,
                        contentDescription = null,
                        tint = SeverityBarColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Severity Assessment",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceCharcoal
                    )
                }

                // High Alert Pill
                Surface(
                    shape = RoundedCornerShape(50),
                    color = ErrorContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PriorityHigh,
                            contentDescription = null,
                            tint = OnErrorContainer,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "High Alert",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = OnErrorContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(SurfaceContainerLow, RoundedCornerShape(50))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // 80% full
                        .fillMaxHeight()
                        .background(SeverityBarColor, RoundedCornerShape(50))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Vehicle temperature exceeds 105°C. Potential coolant leak identified.",
                fontSize = 14.sp,
                color = OnSurfaceVariantGrey
            )
        }
    }
}

@Composable
private fun TrackingMessageBubble(timestamp: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp),
            color = SurfaceContainerLow,
            border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "I've dispatched a premium roadside recovery unit to your precise location.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = OnSurfaceCharcoal
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Embedded Tracking Card
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(PrimaryContainer.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalShipping,
                                    contentDescription = null,
                                    tint = PrimaryGreen
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("ETA: 14 Minutes", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                                Text("Unit #882 is en route", fontSize = 12.sp, color = OnSurfaceVariantGrey)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Fake Map Image Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.DarkGray, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Map Overlay Pill
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = Color.White,
                                shadowElevation = 4.dp
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    // Pulsing dot
                                    val infiniteTransition = rememberInfiniteTransition()
                                    val alpha by infiniteTransition.animateFloat(
                                        initialValue = 0.3f,
                                        targetValue = 1f,
                                        animationSpec = infiniteRepeatable(
                                            animation = tween(1000),
                                            repeatMode = RepeatMode.Reverse
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(PrimaryGreen.copy(alpha = alpha), CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Live Tracking", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceCharcoal)
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = timestamp,
            fontSize = 12.sp,
            color = OutlineVariant,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun ChatInputArea() {
    var text by remember { mutableStateOf("") }

    // Pulsing Red Dot for Emergency Status
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = OutlineVariant.copy(alpha = 0.2f))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Status Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size((8 * pulseScale).dp).background(AlertRed, CircleShape))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Emergency Support Active", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceCharcoal)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Call, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Call Agent", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = PrimaryGreen)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Field
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = SurfaceContainerLow,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(fontSize = 16.sp, color = OnSurfaceCharcoal),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text("Type a message...", color = OutlineVariant, fontSize = 16.sp)
                        }
                        innerTextField()
                    },
                    cursorBrush = SolidColor(PrimaryGreen)
                )

                IconButton(
                    onClick = { /* TODO: Send message */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(PrimaryGreen, RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AssistantTopBar() {
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
            tint = OnSurfaceVariantGrey,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun AssistantBottomNav(navController: NavController) {
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
            onClick = { /* Add detection route here later */ },
            icon = { Icon(Icons.Outlined.Visibility, contentDescription = "Detection") },
            label = { Text("Detection") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("assistant") }, // <-- Route added
            icon = { Icon(Icons.Default.Emergency, contentDescription = "Assistance") },
            label = { Text("Assistance") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                selectedTextColor = PrimaryGreen,
                indicatorColor = PrimaryContainer.copy(alpha = 0.2f)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("settings") }, // <-- Route added
            icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}