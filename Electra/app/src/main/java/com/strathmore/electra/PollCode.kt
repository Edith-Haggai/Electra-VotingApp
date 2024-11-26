package com.strathmore.electra

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun PollVerificationScreen(
    onBackClick: () -> Unit = {}, // Callback for back navigation
    onVerify: () -> Unit = {} // Callback for when the "Verify" button is clicked

) {
    var pollCode by remember { mutableStateOf("") }
    var isHumanChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E88E5), Color(0xFF90CAF9))
                )
            )
            .padding(16.dp)
    ) {
        // Back arrow at the top
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header Text
            Text(
                text = "Poll Verification",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Poll code input field
            TextField(
                value = pollCode,
                onValueChange = { pollCode = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text("Enter Poll Code", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox for "I am human"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isHumanChecked,
                    onCheckedChange = { isHumanChecked = it },
                    colors = CheckboxDefaults.colors(checkmarkColor = Color(0xFF1E88E5))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "I am human",
                    color = Color.White,
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // "Verify" button, only visible when checkbox is checked
            if (isHumanChecked) {
                Button(
                    onClick =  {
                        if (pollCode.isNotEmpty()) {
                            onVerify()
                        } else {
                            Toast.makeText(cotext, "Enter a valid poll code.", Toast.LENGTH_SHORT).show()
                        }
                    },
                        enabled = pollCode.isNotEmpty()
                        ) {
                            Text("Verify")
                        }},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
                    enabled = pollCode.isNotEmpty() // Ensure poll code is entered
                ) {
                    Text(
                        text = "Verify",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PollVerificationScreenPreview() {
    PollVerificationScreen()
}
