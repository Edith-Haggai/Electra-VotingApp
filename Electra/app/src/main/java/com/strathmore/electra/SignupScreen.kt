package com.strathmore.electra

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strathmore.electra.components.HeaderText
import com.strathmore.electra.components.LoginTextField

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onPolicyClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    // State holders for input fields
    val (firstName, onFirstNameChange) = rememberSaveable { mutableStateOf("") }
    val (lastName, onLastNameChange) = rememberSaveable { mutableStateOf("") }
    val (email, onEmailChange) = rememberSaveable { mutableStateOf("") }
    val (password, onPasswordChange) = rememberSaveable { mutableStateOf("") }
    val (confirmPassword, onConfirmPasswordChange) = rememberSaveable { mutableStateOf("") }
    val (agree, onAgreeChange) = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val databaseHelper = DatabaseHelper(context)

    var isPasswordMismatch by remember { mutableStateOf(false) }
    val areFieldsFilled = firstName.isNotEmpty() && lastName.isNotEmpty() &&
            email.isNotEmpty() && password.isNotEmpty() &&
            confirmPassword.isNotEmpty() && agree

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.bg2),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp), // Removed the vertical padding here to make space for the header
            horizontalAlignment = Alignment.Start
        ) {
            // Back Button (Top-left)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 16.dp) // Padding for the top of the screen
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(end = 8.dp) // Padding between the icon and header text
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Sign Up Header (Next to the back arrow)
                HeaderText(
                    text = "Sign Up",
                    modifier = Modifier
                        .align(Alignment.CenterVertically) // Aligning vertically with the arrow
                )
            }

            // Input Fields
            LoginTextField(
                value = firstName,
                onValueChange = onFirstNameChange,
                labelText = "First Name",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            LoginTextField(
                value = lastName,
                onValueChange = onLastNameChange,
                labelText = "Last Name",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            LoginTextField(
                value = email,
                onValueChange = onEmailChange,
                labelText = "Email",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            LoginTextField(
                value = password,
                onValueChange = onPasswordChange,
                labelText = "Password",
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            LoginTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                labelText = "Confirm Password",
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            // Password mismatch message
            AnimatedVisibility(visible = isPasswordMismatch) {
                Text(
                    text = "Passwords do not match",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Terms & Conditions Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Checkbox(
                    checked = agree,
                    onCheckedChange = onAgreeChange
                )
                Spacer(Modifier.width(8.dp))
                val privacyPolicyText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) { append("I agree to the ") }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        pushStringAnnotation("privacy", "Privacy Policy")
                        append("Privacy Policy")
                        pop()
                    }
                    withStyle(style = SpanStyle(color = Color.Gray)) { append(" and ") }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        pushStringAnnotation("policy", "Policy")
                        append("Policy")
                        pop()
                    }
                }
                ClickableText(
                    text = privacyPolicyText,
                    onClick = { offset ->
                        privacyPolicyText.getStringAnnotations(
                            tag = "privacy",
                            start = offset,
                            end = offset
                        ).forEach { onPrivacyClick() }
                        privacyPolicyText.getStringAnnotations(
                            tag = "policy",
                            start = offset,
                            end = offset
                        ).forEach { onPolicyClick() }
                    }
                )
            }

            // Sign Up Button
            Button(
                onClick = {
                    isPasswordMismatch = password != confirmPassword
                    if (!isPasswordMismatch) {
                        // Save user to the database
                        val result = databaseHelper.insertUser(firstName, lastName, email, password)
                        if (result != -1L) {
                            Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error creating user", Toast.LENGTH_SHORT).show()
                        }
                    }
                },

                enabled = areFieldsFilled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Sign Up")
            }

            Spacer(Modifier.height(16.dp))

            // Already have an account
            val signInText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) { append("Already have an account? ") }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    pushStringAnnotation("login", "Sign In")
                    append("Sign In")
                    pop()
                }
            }
            ClickableText(
                text = signInText,
                onClick = { offset ->
                    signInText.getStringAnnotations("login", start = offset, end = offset)
                        .forEach { onLoginClick() }
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}

