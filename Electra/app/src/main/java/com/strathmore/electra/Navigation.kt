package com.strathmore.electra

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome" // The welcome screen is the starting destination
    ) {
        // Welcome Screen
        composable("welcome") {
            WelcomeScreen(
                onGetStartedClick = { navController.navigate("signup") },  // Navigate to signup
                onSignInClick = { navController.navigate("login") }        // Navigate to login
            )
        }

        // Login Screen
        composable("login") {
            LoginScreen(
                onSignUpClick = { navController.navigate("signup") },    // Navigate to signup from login
                onBackClick = { navController.popBackStack() },            // Go back to the previous screen
                onLoginSuccess = { navController.navigate("main") }       // Navigate to main page after login success
            )
        }

        // Sign Up Screen
        composable("signup") {
            SignUpScreen(
                onSignUpClick = { /* Handle sign-up logic */ },
                onLoginClick = { navController.navigate("login") },    // Navigate to login page
                onBackClick = { navController.popBackStack() }           // Go back to the previous screen
            )
        }

        // Main Page
        composable("main") {
            MainPage(
                onNavigateToProfile = { navController.navigate("profile") } // Navigate to profile page
            )
        }

        // Profile Screen
        composable("profile") {
            ProfileScreen(
                onBackToMain = { navController.navigate("main") },  // Navigate back to main page from profile
                onLogout = { navController.navigate("login") },      // Navigate to login page after logout
                onNavigateToHelp = { navController.navigate("help") } ,// Navigate to help page
                onNavigateToPollVerification = { navController.navigate("pollVerification") },
                onNavigateToCreatePoll = { navController.navigate("createPoll") },
                onNavigateToOngoingElections = { navController.navigate("ongoingElections") }
           )
        }
        composable("createPoll") {
            CandidateDetailsScreen(
                onSubmit = { name, position, manifesto ->
                    // Handle the candidate submission logic here
                }
            )
        }
        composable("pollVerification") {
            PollVerificationScreen(
                onBackClick = { navController.popBackStack() },
                onVerify = { navController.navigate("votingScreen") } // Navigate to VotingScreen
            )
        }

        // Voting Screen
        composable("votingScreen") {
            val candidates = listOf(
                "Candidate 1" to R.drawable.candidate_one,
                "Candidate 2" to R.drawable.candidate_two,
                "Candidate 3" to R.drawable.candidate_one,
                "Abstain" to 0
            )
            VotingScreen(
                candidates = candidates,
                onNextClicked = { selectedOption ->
                    // Handle the next action
                }
            )
        }

        // Help Screen
        composable("help") {
            HelpSection(
                onBackToProfile = { navController.popBackStack() } // Go back to the Profile Screen
            )
        }
        // Poll Verification Screen
        composable("pollVerification") {
            PollVerificationScreen(
                onBackClick = { navController.popBackStack() },  // Go back to the profile screen
                onVerify = { /* Handle verification logic */ }
            )
        }


    }
}
