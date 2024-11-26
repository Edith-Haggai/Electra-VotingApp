package com.strathmore.electra


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainPage(onNavigateToProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome Text
        Text(
            text = "Welcome to Electra",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EA),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        // Voting Image
        Image(
            painter = painterResource(id = R.drawable.voting), // Replace with your image resource
            contentDescription = "People Voting",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        // About Section
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "About Electra",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6200EA),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Electra is an innovative voting platform designed to simplify elections and polls. "
                            + "Our goal is to promote fairness, transparency, and user convenience in the voting process. "
                            + "Start creating or participating in elections with just a few clicks!",
                    fontSize = 16.sp,
                    color = Color(0xFF333333),
                    lineHeight = 22.sp
                )
            }
        }

        // Navigate to Profile Button
        Button(
            onClick = onNavigateToProfile,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Go to Profile",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MainPage(onNavigateToProfile = { /* Navigate to profile logic */ })
}
