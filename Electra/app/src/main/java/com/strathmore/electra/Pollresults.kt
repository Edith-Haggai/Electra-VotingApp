package com.strathmore.electra



import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PollResultsScreen() {
    var timerValue by remember { mutableStateOf(60) }  // 60 seconds timer
    var totalVotes by remember { mutableStateOf(0) }
    var spoilVotes by remember { mutableStateOf(0) }
    var registeredVoters by remember { mutableStateOf(0) }
    var unusedVotes by remember { mutableStateOf(0) }
    val pollItems = remember { List(10) { "Item ${it + 1}" } }

    // Timer Countdown
    LaunchedEffect(timerValue) {
        if (timerValue > 0) {
            delay(1000)
            timerValue -= 1
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text("Poll Results", style = MaterialTheme.typography.headlineSmall, color = Color.Green)

        Spacer(modifier = Modifier.height(16.dp))

        // Poll Items
        Column(modifier = Modifier.fillMaxWidth()) {
            pollItems.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item, fontSize = 18.sp)
                    Text("${(10 - index)} Votes", fontSize = 16.sp) // Example ranking based on index
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Poll Stats
        PollStats(
            totalVotes = totalVotes,
            spoilVotes = spoilVotes,
            registeredVoters = registeredVoters,
            unusedVotes = unusedVotes
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Timer
        Text("Time Left ${formatTime(timerValue)}", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Exit Button
        Button(
            onClick = { /* Handle exit */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("EXIT TO HOME", color = Color.White)
        }
    }
}

@Composable
fun PollStats(
    totalVotes: Int,
    spoilVotes: Int,
    registeredVoters: Int,
    unusedVotes: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PollStatItem(label = "Total Votes", value = totalVotes)
        PollStatItem(label = "Spoilt Votes", value = spoilVotes)
        PollStatItem(label = "Registered Voters", value = registeredVoters)
        PollStatItem(label = "Unused Votes", value = unusedVotes)
    }
}

@Composable
fun PollStatItem(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        Text("$value", fontSize = 16.sp)
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@Preview
@Composable
fun PollResultsScreenPreview() {
    PollResultsScreen()
}
