package com.example.disastermanagementapp.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Shared Theme Colors
private val PrimaryGreen = Color(0xFF006D36)
private val PrimaryDarkGreen = Color(0xFF005227)
private val AppBackground = Color(0xFFF9F9F9)
private val SurfaceColor = Color(0xFFFFFFFF)
private val OnSurfaceCharcoal = Color(0xFF1A1C1C)
private val OnSurfaceVariantGrey = Color(0xFF3E4A3F)
private val OutlineVariant = Color(0xFFBDCABC)
private val TextFieldBackground = Color(0xFFF0F0F0) // Matches the #F9F9F9 inner form feel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Top App Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceColor)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "SafeDrive AI Logo",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SafeDrive AI",
                    color = PrimaryGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(onClick = { /* TODO: Help Action */ }) {
                Icon(
                    imageVector = Icons.Outlined.HelpOutline,
                    contentDescription = "Help",
                    tint = OnSurfaceVariantGrey
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // --- Hero Visual Section ---
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFE0F2E9)), // Soft green background
                contentAlignment = Alignment.Center
            ) {
                // Placeholder for the 3D shield render
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome Back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceCharcoal
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Sign in to continue your safe journey.",
                fontSize = 14.sp,
                color = OnSurfaceVariantGrey
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Form Section ---
            // Email Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Email Address",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnSurfaceVariantGrey,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("driver@safedrive.ai", color = OutlineVariant) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = OnSurfaceVariantGrey)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = TextFieldBackground,
                        unfocusedContainerColor = TextFieldBackground,
                        focusedIndicatorColor = PrimaryGreen,
                        unfocusedIndicatorColor = OutlineVariant,
                        focusedTextColor = OnSurfaceCharcoal,
                        unfocusedTextColor = OnSurfaceCharcoal
                    ),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Password",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnSurfaceVariantGrey,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••", color = OutlineVariant) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Lock Icon", tint = OnSurfaceVariantGrey)
                    },
                    trailingIcon = {
                        val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(icon, contentDescription = "Toggle Password Visibility", tint = OnSurfaceVariantGrey)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = TextFieldBackground,
                        unfocusedContainerColor = TextFieldBackground,
                        focusedIndicatorColor = PrimaryGreen,
                        unfocusedIndicatorColor = OutlineVariant,
                        focusedTextColor = OnSurfaceCharcoal,
                        unfocusedTextColor = OnSurfaceCharcoal
                    ),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
            }

            // Forgot Password
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(onClick = onForgotPasswordClick, contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = "Forgot Password?",
                        color = PrimaryGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sign In Button
            Button(
                onClick = onSignInClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(PrimaryGreen, PrimaryDarkGreen)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // --- Divider ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = OutlineVariant.copy(alpha = 0.5f))
                Text(
                    text = "OR",
                    color = OutlineVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = OutlineVariant.copy(alpha = 0.5f))
            }

            // --- Social Logins ---
            OutlinedButton(
                onClick = { /* TODO: Samsung Login */ },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceCharcoal),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                // Note: Replace with painterResource(id = R.drawable.ic_samsung) when you have the icon
                Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Continue with Samsung", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { /* TODO: Google Login */ },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceCharcoal),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                // Note: Replace with painterResource(id = R.drawable.ic_google) when you have the icon
                Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Continue with Google", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Create Account Link ---
            Text(
                text = buildAnnotatedString {
                    append("Don't have an account?  ")
                    withStyle(style = SpanStyle(color = PrimaryGreen, fontWeight = FontWeight.Bold)) {
                        append("Create Account")
                    }
                },
                fontSize = 14.sp,
                color = OnSurfaceVariantGrey,
                modifier = Modifier.clickable(onClick = onCreateAccountClick)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // --- Security Badge Footer ---
            Surface(
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f)),
                color = Color.Transparent,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = "Verified",
                        tint = PrimaryGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "256-bit AES Encrypted Data Protection",
                        fontSize = 12.sp,
                        color = OnSurfaceVariantGrey
                    )
                }
            }
        }
    }
}