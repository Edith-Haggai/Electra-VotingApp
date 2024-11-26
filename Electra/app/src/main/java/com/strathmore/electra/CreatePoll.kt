package com.strathmore.electra

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strathmore.electra.R
import androidx.compose.ui.tooling.preview.Preview // Ensure this is imported correctly


@OptIn(ExperimentalMaterial3Api::class)
class CandidateDetailsActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private var pollId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pollId = intent.getLongExtra("pollId", -1)
        if (pollId == -1L) {
            Toast.makeText(this, "Invalid Poll ID", Toast.LENGTH_SHORT).show()
            finish() // Exit if pollId is invalid
            return
        }


        dbHelper = DatabaseHelper(this) // Initialize DatabaseHelper

        setContent {
            MaterialTheme {
                CandidateDetailsScreen(
                    onSubmit = { name, position, manifesto ->
                        // Create a Candidate object with the input details
                        val candidateId = dbHelper.insertCandidate(name, position, manifesto,pollId)
                        // Show a Toast indicating the candidate has been added
                        Toast.makeText(
                            this,
                            "Candidate Added: Name: $name, Position: $position, Manifesto: $manifesto",
                            Toast.LENGTH_LONG
                        ).show()

                        // Optionally clear the input fields for new candidate
                    }
                )
            }
        }
    }
}


@Composable
fun CandidateDetailsScreen(
    onSubmit: (name: String, position: String, manifesto: String) -> Unit,

) {
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var manifesto by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Handle submit logic
    val submitCandidate: () -> Unit = {
        onSubmit(name, position, manifesto)

        // After submission, navigate to SetupPollsActivity
        val intent = Intent(context, SetupPollsActivity::class.java)
        context.startActivity(intent)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x07009688)) // Background color
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top View
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                // Image as a background
                Image(
                    painter = painterResource(id = R.drawable.bg2),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Adjust based on your image type
                )
            }

            // Title Text
            Text(
                text = "Edit Candidate Details",
                color = Color.Black,
                fontSize = 30.sp,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Candidate Name Input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text(text = "Enter Candidate Name") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Candidate Position Input
            OutlinedTextField(
                value = position,
                onValueChange = { position = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text(text = "Enter Position") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Candidate Manifesto Input
            OutlinedTextField(
                value = manifesto,
                onValueChange = { manifesto = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(150.dp),
                label = { Text(text = "Enter Manifesto") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Candidate Image Placeholder
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_3),
                    contentDescription = "Candidate Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Upload Image Button
            Button(
                onClick = { /* TODO: Implement image upload */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Upload Image")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit and Add Candidate Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Logic for adding another candidate */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                ) {
                    Text(text = "Add Candidate", color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick =  submitCandidate,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text(text = "Submit", color = Color.White)
                }
            }
        }

    }
}

// Preview for CandidateDetailsScreen
@Preview(showBackground = true)
@Composable
fun CandidateDetailsScreenPreview() {
    CandidateDetailsScreen(
        onSubmit = { name, position, manifesto ->
            // Handle the submit action in the preview (if needed)
        }
        )
}