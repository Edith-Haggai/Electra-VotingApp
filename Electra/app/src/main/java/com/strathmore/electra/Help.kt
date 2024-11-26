package com.strathmore.electra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HelpSection(onBackToProfile: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)) // Light background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Enable scrolling for large content
        ) {
            // Back Button to go back to Profile Screen
            IconButton(
                onClick = { onBackToProfile() },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24), // Arrow back icon
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            // Header Section
            Text(
                text = "Help Section",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF32CD32), // Lime Green
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp) // Extra space below the header
            )

            // Instructions Section
            HelpCard(
                title = "How to Start a Poll",
                steps = listOf(
                    "1. Navigate to the 'Create Poll' page from the home screen.",
                    "2. Enter the required details like poll title, description, and options for voting.",
                    "3. Set the poll duration (e.g., 1 hour, 1 day).",
                    "4. Click the 'Start Poll' button to initiate the poll.",
                    "5. Share the poll ID or link with participants."
                )
            )

            Spacer(modifier = Modifier.height(24.dp)) // Increased space between sections

            HelpCard(
                title = "How to Cast a Vote",
                steps = listOf(
                    "1. Open the app and navigate to the 'Vote Now' section.",
                    "2. Enter the poll ID or select the poll from the active polls list.",
                    "3. Review the list of candidates and their details.",
                    "4. Select your preferred candidate using the radio buttons.",
                    "5. Click the 'Submit Vote' button to finalize your vote.",
                    "6. Ensure you receive the confirmation message indicating your vote was cast successfully."
                )
            )
        }
    }
}

@Composable
fun HelpCard(title: String, steps: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Adds space between cards
            .background(Color.White),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5) // Light gray background for the card
        ),
        elevation = CardDefaults.cardElevation(10.dp) // Increased elevation for a more prominent card
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Section Title
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF32CD32), // Lime Green
                modifier = Modifier.padding(bottom = 8.dp) // Padding between title and content
            )

            // Steps List
            steps.forEachIndexed { index, step ->
                Text(
                    text = step,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 6.dp), // Added consistent bottom padding for steps
                    color = Color.Black // Black color for text for better readability
                )
            }
        }
    }
}

@Preview
@Composable
fun HelpSectionPreview() {
    HelpSection(onBackToProfile = {})
}
