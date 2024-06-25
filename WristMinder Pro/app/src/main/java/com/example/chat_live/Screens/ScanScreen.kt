package com.example.chat_live.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_live.CLViewModel


@Composable
fun ScanScreen(navController: NavController, vm: CLViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2C7FF))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Scan your X-RAY",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF455A64)),
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(vertical = 50.dp))
        CameraButton(
            modifier = Modifier.size(200.dp),
            iconResourceId = com.example.chat_live.R.drawable.camera_icom, // Assuming R.drawable.camera_icon is your XML drawable resource
            onClick = { /* Handle button click */ }
        )
    }
}

@Composable
fun CameraButton(
    modifier: Modifier = Modifier,
    iconResourceId: Int,
    onClick: () -> Unit
) {
    val iconPainter: Painter = painterResource(id = iconResourceId)
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape
    ) {
        Image(
            painter = iconPainter,
            contentDescription = "Camera",
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun scanpr(){

}