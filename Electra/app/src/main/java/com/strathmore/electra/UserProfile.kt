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

@Composable
fun ProfileScreen(
    onBackToMain: () -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateToHelp: () -> Unit = {},
    onNavigateToPollVerification: () -> Unit = {},
    onNavigateToCreatePoll: () -> Unit = {},
    onNavigateToOngoingElections: () -> Unit = {}

) {
    // Scrollable container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Keep the height of the background image
        ) {
            // Top Background Image
            Image(
                painter = painterResource(id = R.drawable.top_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Profile Image
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomCenter) // Align at the bottom center of the Box
                    .offset(y = 60.dp) // Push it up to overlap with the background
            )
        }

        // Name and Email
        Text(
            text = "Brian Bona",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp)
        )
        Text(
            text = "bona@gmail.com",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Buttons Section
        ButtonWithIcon("Cast a Vote", R.drawable.logo){
            onNavigateToPollVerification()
        }
        ButtonWithIcon("Create Poll", R.drawable.poll){
            onNavigateToCreatePoll()
        }
        ButtonWithIcon("Ongoing Elections", R.drawable.ic_3){
            onNavigateToOngoingElections()
        }
        ButtonWithIcon("History", R.drawable.history){}

        ButtonWithIcon("Help", R.drawable.help){
            onNavigateToHelp()
        }

        ButtonWithIcon("logout", R.drawable.logout){
            onLogout()
        }



        Spacer(modifier = Modifier.height(4.dp))

        // Back to Main Page Button
        Button(
            onClick =onBackToMain,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Back to Main Page",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ButtonWithIcon(text: String,
                   iconRes: Int,
                   onClick:() -> Unit
                   ) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White), // White background
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp), // Subtle elevation
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon on the left
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            // Text in black, centered
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black, // Set text color to black
                modifier = Modifier.weight(1f) // Center-align the text within the button
            )
        }
    }
}



@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
