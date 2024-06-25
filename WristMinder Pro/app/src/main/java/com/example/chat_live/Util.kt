package com.example.chat_live

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter


fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        CircularProgressIndicator()

    }
}


@Composable
fun CommonDivider() {

    Divider(
        color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )

}

@Composable
fun CheckSignedIn(vm: CLViewModel, navController: NavController) {


    val alreadySignIn = remember {
        mutableStateOf(false)
    }
    val SignIn = vm.signIN.value

    if (SignIn && !alreadySignIn.value) {
        alreadySignIn.value = true
        navController.navigate(DestinationScreen.ChatList.route)
        {
            popUpTo(0)
        }
    }

}

@Composable
fun TitleText(txt: String) {
    Text(
        text = txt,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
) {

    Log.e("Chat-Live Application",data.toString())
    val painter = rememberImagePainter(data = data)
    Image(
        painter = painter,
        contentDescription = "NOt Found",
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun CommonRow(imageUrl: String?, name: String?, onItemClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .border(
                width = 2.dp,
                color = Color(0xFFD1C4E9), // Example color (orange), you can change this to any eye-catching color you like
                shape = CircleShape // You can change the shape if needed
            )
            .clickable {

                onItemClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imageUrl?.isEmpty()==false) {
            CommonImage(
                data = imageUrl,

                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        Color.Red
                    )
            )
        }
        else {
            val initials = name?.split(" ")?.mapNotNull { it.firstOrNull()?.toString() }?.take(3)?.joinToString("") ?: "NN"
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = initials.uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        Text(
            text = name ?: "-----",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start=4.dp)
        )
    }

}
