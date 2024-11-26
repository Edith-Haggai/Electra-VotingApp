package com.strathmore.electra



import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strathmore.electra.R
import kotlin.collections.List
import kotlin.collections.forEach

@Composable
fun VotingScreen(
    candidates: List<Pair<String, Int>>, // Candidate name and image resource
    onNextClicked: (String) -> Unit // Callback when Next button is clicked
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Candidature Position",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF32CD32), // Lime Green
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons with images
        candidates.forEach { candidate ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .selectable(
                        selected = (selectedOption == candidate.first),
                        onClick = { selectedOption = candidate.first }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedOption == candidate.first),
                    onClick = { selectedOption = candidate.first },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF32CD32)) // Lime Green
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = candidate.first,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (candidate.first != "Abstain") {
                    Image(
                        painter = painterResource(id = candidate.second),
                        contentDescription = "Candidate Image",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Handle previous button logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "PREVIOUS", color = Color.White)
            }
            Button(
                onClick = {
                    if (selectedOption == null) {
                        Toast.makeText(context, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show()
                    } else {
                        onNextClicked(selectedOption!!)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "NEXT", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun VotingScreenPreview() {
    // Mock data
    val candidates = listOf(
        "Candidate 1" to R.drawable.candidate_one,
        "Candidate 2" to R.drawable.candidate_two,
        "Candidate 3" to R.drawable.candidate_one,
        "Abstain" to 0 // No drawable resource for "Abstain"
    )

    // Display the VotingScreen with mock data
    VotingScreen(
        candidates = candidates,
        onNextClicked = { selectedOption -> /* Handle Next Click */ }
    )
}