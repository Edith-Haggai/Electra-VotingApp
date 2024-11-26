package com.strathmore.electra


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Poll(
    val title: String,
    val description: String,
    val participationDate: String
)

@Composable
fun PollsParticipatedScreen(polls: List<Poll>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Polls Participated In",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(polls.size) { index ->
                PollItem(poll = polls[index])
            }
        }
    }
}

@Composable
fun PollItem(poll: Poll) {
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
                text = "Participated on: ${poll.participationDate}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview
@Composable
fun PollsParticipatedPreview() {
    PollsParticipatedScreen(
        polls = listOf(
            Poll(
                title = "Student Council Election",
                description = "Vote for your representative.",
                participationDate = "2024-11-01"
            ),
            Poll(
                title = "Class President Election",
                description = "Decide the next class president.",
                participationDate = "2024-10-15"
            )
        )
    )
}