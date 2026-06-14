package com.example.disastermanagementapp.ui.screens.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Replace the broken Color variables with these ---
val PrimaryGreen = Color(0xFF006D36)
val PrimaryContainerGreen = Color(0xFF50C878)
val AppBackground = Color(0xFFF9F9F9)
val OnSurfaceCharcoal = Color(0xFF1A1C1C)
val OnSurfaceVariantGrey = Color(0xFF3E4A3F)
val OutlineVariant = Color(0xFFBDCABC)

@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    // Pulse animation state for the concentric monitoring rings
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Top Navigation Anchor / Brand Header ---
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.height(64.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Shield Logo",
                tint = PrimaryGreen,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "SafeDrive AI",
                color = PrimaryGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.5).sp
            )
        }

        // --- Main Presentation Body ---
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // High-Fidelity Glass/Ambient Card Illustration Container
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.8f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Pulsing Ring Effect
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .scale(pulseScale)
                            .alpha(pulseAlpha)
                            .background(PrimaryGreen.copy(alpha = 0.05f), CircleShape)
                    )

                    // Core Shield & Heart Icon Centerpiece
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .background(PrimaryContainerGreen.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = PrimaryGreen,
                                modifier = Modifier.size(80.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Safety Monitoring Status",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(28.dp)
                                    .offset(y = (-4).dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Tagline Headlines
                Text(
                    text = "Your intelligent co-pilot for every journey.",
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceCharcoal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Feature Sub-description text
                Text(
                    text = "Crash detection, biometric monitoring, and AI roadside assistance.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = OnSurfaceVariantGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 320.dp)
                )
            }
        }

        // --- Action Elements Block ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Primary Interactive Gradient Button
            Button(
                onClick = onGetStartedClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(PrimaryContainerGreen, PrimaryGreen)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Get Started",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Context Clickable Log In Link
            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(style = SpanStyle(color = PrimaryGreen, fontWeight = FontWeight.Bold)) {
                        append("Log In")
                    }
                },
                fontSize = 14.sp,
                color = OnSurfaceVariantGrey,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onLoginClick
                    )
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Structural Footer Meta Identity ---
        Text(
            text = "Built with safety in mind. © 2024 SafeDrive AI",
            fontSize = 12.sp,
            color = OutlineVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}