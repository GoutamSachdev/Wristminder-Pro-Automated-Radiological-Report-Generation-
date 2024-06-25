package com.example.chat_live.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chat_live.CLViewModel
import com.example.chat_live.CommonDivider
import com.example.chat_live.CommonProgressBar
import com.example.chat_live.CommonRow
import com.example.chat_live.DestinationScreen
import com.example.chat_live.TitleText
import com.example.chat_live.navigateTo

@Composable
fun StatusScreen(navController: NavController, vm: CLViewModel) {
    val context = LocalContext.current
    val inprogressStatus = vm.inprogressStatus.value
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { 
        uri ->
        uri?.let{
            vm.uploadStatus(uri)
        }
        
        
    }
    if (false) {
        CommonProgressBar()
    } else {
        val status = vm.status.value
        val UserData = vm.userData.value
        val myStatuss = status.filter {
            it.user.userId == UserData?.userId
        }

        val OtherStatus = status.filter {
            it.user.userId != UserData?.userId
        }




        Scaffold(
            modifier = Modifier.fillMaxWidth().background(Color(0XFFD2C7FF)),
            topBar = { TitleText("Status")
                CommonDivider()},

            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFFEAE5FF),
                    contentColor = Color.Black,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomBarItem(
                            icon = Icons.Default.Home,
                            text = "Home",
                            isActive = false,
                            onClick = { navigateTo(navController, DestinationScreen.ChatList.route) }
                        )
                        BottomBarItem(
                            icon = Icons.Default.List,
                            text = "Activity",
                            isActive = true,
                            onClick = { }
                        )
                        BottomBarItem(
                            icon = Icons.Default.MoreVert,
                            text = "More",
                            isActive = false,
                            onClick = { navigateTo(navController, DestinationScreen.MoreScreen.route)}
                        )
                    }
                }
            },
            content = { it ->
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    if (status.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "NO Status Available")
                        }
                    } else {

                        if (myStatuss.isNotEmpty()) {
                            CommonRow(
                                imageUrl = myStatuss[0].user.imageUrl,
                                name = myStatuss[0].user.name
                            ) {
                                navigateTo(
                                    navController,
                                    DestinationScreen.SingleStatus.createRoute(myStatuss[0].user.userId!!)
                                )
                            }
                            CommonDivider()

                            val uniqueUSer = OtherStatus.map {
                                it.user
                            }.toSet().toList()
                            LazyColumn(modifier=Modifier.weight(1f)) {
                                items(uniqueUSer){
                                    user->
                                    CommonRow(imageUrl =  user.imageUrl, name =user.name ) {
                                        navigateTo(
                                            navController,
                                            DestinationScreen.SingleStatus.createRoute(user.userId!!)
                                        )
                                    }
                                }

                            }
                        }
                    }
                }


            }
        )
    }


}

@Composable
fun Fab(
    onFabCLick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onFabCLick() },
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = CircleShape,
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Edit,
            contentDescription = "Add Status",
            tint = Color.White
        )

    }
}