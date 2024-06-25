package com.example.chat_live.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_live.DestinationScreen
import com.example.chat_live.navigateTo
import kotlinx.coroutines.delay

@Composable
fun SplashScreenn(navController: NavController){
    LaunchedEffect(Unit) {

        delay(3000) // Wait for 3 seconds

        navigateTo(navController, DestinationScreen.DownloadReport.route)

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2C7FF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .aspectRatio(1.5f), // Aspect ratio to ensure consistent sizing
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "WRIST MINDER PRO:\n INJURY REPORT GENERATION",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 20.dp)
                )

                // Add loading icon below text
                CircularProgressIndicator(
                    color = Color(0xFFD9D0FA),
                    trackColor = Color.Black,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }


}