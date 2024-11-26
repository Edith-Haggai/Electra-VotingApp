package com.strathmore.electra

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

class SetupPollsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = DatabaseHelper(this)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                SetupPollsScreen(
                    onCreatePoll = { generatedVotingCode, duration ->
                        val pollId = dbHelper.insertPoll(generatedVotingCode, duration)
                        Toast.makeText(this, "Poll Created", Toast.LENGTH_SHORT).show()
                        navController.navigate("pollCountdown/$pollId")
                    },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun SetupPollsScreen(
    onCreatePoll: (String, String) -> Unit,
    navController: NavController
) {
    var votingCode by remember { mutableStateOf("") }
    var selectedDuration by remember { mutableStateOf("30 minutes") }
    val durations = listOf("30 minutes", "2 hours", "6 hours", "24 hours")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Setup Poll",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Generate Voting Code
        Button(
            onClick = {
                votingCode = (100000..999999).random().toString()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Voting Code")
        }

        Text(text = "Voting Code: $votingCode", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Voting Duration
        Text(
            text = "Select Voting Duration",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))

        durations.forEach { duration ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedDuration == duration),
                    onClick = { selectedDuration = duration }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = duration, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Poll
        Button(
            onClick = {
                if (votingCode.isNotBlank()) {
                    onCreatePoll(votingCode, selectedDuration)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Poll")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("candidateDetails") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Candidate Details")
        }
    }
}
