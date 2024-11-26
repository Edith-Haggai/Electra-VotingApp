package com.strathmore.electra

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strathmore.electra.components.HeaderText
import com.strathmore.electra.components.LoginTextField

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onLoginSuccess: () ->Unit ={}

) {
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (checked, onCheckedChange) = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val databaseHelper = DatabaseHelper(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.bg2),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp) // Adjust padding to your preference
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = Color.Black // Change color to contrast with the background
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(defaultPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(R.drawable.logo1),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(bottom = itemSpacing)
            )


            HeaderText(
                text = "Sign In",
                modifier = Modifier
                    .padding(vertical = defaultPadding)
                    .align(alignment = Alignment.Start)
            )

            LoginTextField(
                value = email,
                onValueChange = setEmail,
                labelText = "Email",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(itemSpacing))

            LoginTextField(
                value = password,
                onValueChange = setPassword,
                labelText = "Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(itemSpacing))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                    Text("Remember Me")
                }
                TextButton(onClick = {}) {
                    Text("Forgot Password?")
                }
            }

            Spacer(Modifier.height(itemSpacing))

            Button(onClick = {
                val isValidUser = databaseHelper.getUserByEmailAndPassword(email, password)
                if (isValidUser) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    onLoginSuccess()// Navigate to home screen or main activity
                } else {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            },
                modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }

            Spacer(Modifier.height(itemSpacing))

            AlternativeLoginOptions(
                onIconClick = { index ->
                    when (index) {
                        0 -> Toast.makeText(context, "Instagram Login Click", Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(context, "Google Login Click", Toast.LENGTH_SHORT).show()
                        2 -> Toast.makeText(context, "GitHub Login Click", Toast.LENGTH_SHORT).show()
                    }
                },
                onSignUpClick = onSignUpClick,
                modifier = Modifier.padding(top = defaultPadding)
            )
        }
    }
}

@Composable
fun AlternativeLoginOptions(
    onIconClick: (index: Int) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconList = listOf(
        R.drawable.instagram_brands_solid,
        R.drawable.google_brands_solid,
        R.drawable.github_brands_solid,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Or Sign in With")
        Row(horizontalArrangement = Arrangement.spacedBy(defaultPadding)) {
            iconList.forEachIndexed { index, iconResId ->
                Image(
                    painter = painterResource(iconResId),
                    contentDescription = "Alternative Login",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable { onIconClick(index) }
                )
            }
        }

        Spacer(Modifier.height(itemSpacing))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't Have An Account?")
            Spacer(Modifier.width(itemSpacing))
            TextButton(onClick = onSignUpClick) {
                Text("Sign Up")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
