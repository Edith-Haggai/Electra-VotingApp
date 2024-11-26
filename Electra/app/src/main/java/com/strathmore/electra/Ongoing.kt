package com.strathmore.electra

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

data class OngoingPoll(
    val title: String,
    val description: String,
    val endTime: LocalDateTime
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OngoingElectionsScreen(polls: List<OngoingPoll>) {
    val ongoingPolls = polls.filter { it.endTime.isAfter(LocalDateTime.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ongoing Elections",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (ongoingPolls.isNotEmpty()) {
            LazyColumn {
                items(ongoingPolls.size) { index ->
                    OngoingPollItem(poll = ongoingPolls[index])
                }
            }
        } else {
            Text(
                text = "No ongoing elections at the moment.",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun OngoingPollItem(poll: OngoingPoll) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = poll.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = poll.description,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Ends on: ${poll.endTime}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun OngoingElectionsPreview() {
    OngoingElectionsScreen(
        polls = listOf(
            OngoingPoll(
                title = "Class Representative Election",
                description = "Vote for your class representative.",
                endTime = LocalDateTime.now().plusDays(1)
            ),
            OngoingPoll(
                title = "Sports Captain Election",
                description = "Select the next sports captain.",
                endTime = LocalDateTime.now().plusHours(5)
            )
        )
    )
}