package com.example.disastermanagementapp.ui.screens.dashboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.FrontHand
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// --- UI Color Palette ---
private val PrimaryGreen = Color(0xFF006D36)
private val AppBackground = Color(0xFFF9F9F9)
private val SurfaceContainerLow = Color(0xFFF3F3F3)
private val OnSurfaceCharcoal = Color(0xFF1A1C1C)
private val OnSurfaceVariantGrey = Color(0xFF3E4A3F)
private val OutlineVariant = Color(0xFFBDCABC)
private val HighlightCoral = Color(0xFFFE7E4F) // secondary-container color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrashDetectionScreen(
    onCancelAlert: () -> Unit = {},
    onCallEmergencyNow: () -> Unit = {}
) {
    // --- 15 Second Countdown Timer State ---
    var timeLeft by remember { mutableStateOf(15) }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        } else {
            onCallEmergencyNow()
        }
    }

    // --- Subtle Vibrating Animation for Emergency Button ---
    val infiniteTransition = rememberInfiniteTransition(label = "Vibration")
    val vibrateOffsetFloat by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "VibrateOffset"
    )
    val vibrateOffset = vibrateOffsetFloat.dp

    Scaffold(
        topBar = { CrashTopAppBar() },
        containerColor = AppBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // --- Header Message ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Crash Detected",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceCharcoal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "We've detected a significant impact. Are you okay? Calling emergency services in...",
                    fontSize = 16.sp,
                    color = OnSurfaceVariantGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Central Circular Countdown Timer ---
            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Background track ring
                    drawCircle(
                        color = Color(0xFFEEEEEE),
                        radius = 110.dp.toPx(),
                        style = Stroke(width = 8.dp.toPx())
                    )

                    // Animated progress sweeping ring
                    val sweepAngle = (timeLeft / 15f) * 360f
                    drawArc(
                        color = HighlightCoral,
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx())
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = timeLeft.toString(),
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceCharcoal
                    )
                    Text(
                        text = "SECONDS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceVariantGrey,
                        letterSpacing = 1.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Safety Checklist Guide Card ---
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SurfaceContainerLow,
                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ListAlt,
                            contentDescription = null,
                            tint = PrimaryGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Safety Check",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    }

                    val structuralChecks = listOf(
                        "Check yourself and passengers for injuries.",
                        "Move to a safe location if possible.",
                        "Turn on hazard lights."
                    )

                    structuralChecks.forEach { step ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = PrimaryGreen,
                                modifier = Modifier
                                    .size(18.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = step,
                                fontSize = 14.sp,
                                color = OnSurfaceVariantGrey
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Control / Call Action Triggers ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // I AM OK Outlined Button
                OutlinedButton(
                    onClick = onCancelAlert,
                    border = BorderStroke(2.dp, HighlightCoral),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FrontHand,
                        contentDescription = null,
                        tint = HighlightCoral
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "I AM OK",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HighlightCoral
                    )
                }

                // CALL EMERGENCY NOW Vibrating Button
                Button(
                    onClick = onCallEmergencyNow,
                    colors = ButtonDefaults.buttonColors(containerColor = HighlightCoral),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .offset(x = vibrateOffset, y = vibrateOffset)
                ) {
                    Icon(
                        imageVector = Icons.Default.Emergency,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CALL EMERGENCY NOW",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- Geolocation Live Footer ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = OnSurfaceVariantGrey,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "CURRENT LOCATION",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceVariantGrey,
                        letterSpacing = 1.sp
                    )
                }
                Text(
                    text = "19.281255, 72.855341 • Mira Bhayandar, MH",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurfaceCharcoal,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun CrashTopAppBar() {
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
                contentDescription = null,
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
            contentDescription = "Active Status Monitoring",
            tint = PrimaryGreen,
            modifier = Modifier.size(24.dp)
        )
    }
}