package com.example.chat_live.Screens


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_live.CLViewModel
import com.example.chat_live.DestinationScreen
import com.example.chat_live.navigateTo


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(navController: NavController, vm: CLViewModel) {
    val context = LocalContext.current
    val inProcess = vm.inProcess.value
    val userData = vm.userData.value
    var userId = rememberSaveable {
        mutableStateOf(userData?.userId ?: "")
    }
    var name = rememberSaveable {
        mutableStateOf(userData?.name ?: "")
    }
    var number = rememberSaveable {
        mutableStateOf(userData?.number ?: "")
    }
    val buttonHeight = 58.dp
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu",
                        color = Color(0XFF455A64),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color(0XFFD2C7FF)
                )
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFFD2C7FF))
                    .padding(top = 60.dp, bottom = 76.dp) // Adjust bottom padding to accommodate bottom bar
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0XFFEAE5FF)
                        ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.AccountCircle),
                                contentDescription = "Profile Icon",
                                modifier = Modifier.size(100.dp).clickable {

                                    navigateTo(navController = navController, route = DestinationScreen.Profile.route)

                                }
                            )
                            Text("Hi, ${name.value}", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0XFF455A64)))
                            Text(userId.value,color = Color(0XFF455A64))
                        }
                    }
                }
                item {
                    val buttonHeight = 56.dp
                    Button(
                        onClick = { navController.navigate("invite") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .height(buttonHeight),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFEAE5FF),
                            contentColor = Color(0XFF455A64)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Share,
                                contentDescription = "Invite Icon",
                                modifier = Modifier.size(26.dp))
                            Text("Invite Friend")
                        }
                    }
                }
                item {
                    Button(
                        onClick = {   navigateTo(navController = navController, route = DestinationScreen.Profile.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .height(buttonHeight),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFEAE5FF),
                            contentColor = Color(0XFF455A64)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Settings,
                                contentDescription = "Setting",
                                modifier = Modifier.size(26.dp))
                            Text("Setting         ")
                        }
                    }
                }
                item {
                    Button(
                        onClick = { navController.navigate("help") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .height(buttonHeight),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFEAE5FF),
                            contentColor = Color(0XFF455A64)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Call,
                                contentDescription = "Help",
                                modifier = Modifier.size(26.dp))
                            Text("Help            ")
                        }
                    }
                }
                item {
                    Button(
                        onClick = { navController.navigate("help") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .height(buttonHeight),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFEAE5FF),
                            contentColor = Color(0XFF455A64)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Info,
                                contentDescription = "About us",
                                modifier = Modifier.size(26.dp))
                            Text("About us   ")
                        }
                    }
                }
                item {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = {
                                vm.logout()
                                navigateTo(navController = navController, route = DestinationScreen.Login.route)
                                Toast.makeText(context, "Logout SuccesfullY: ", Toast.LENGTH_SHORT).show()
                                      },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0XFFEAE5FF),
                                contentColor = Color(0XFF455A64)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(horizontal = 16.dp)
                        ) {
                            Text("Logout")
                        }
                    }
                }
            }
        },
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
                        onClick = {  navigateTo(navController = navController, route = DestinationScreen.ChatList.route) }
                    )
                    BottomBarItem(
                        icon = Icons.Default.List,
                        text = "Activity",
                        isActive = false,
                        onClick = {  navigateTo(navController = navController, route = DestinationScreen.StatusList.route) }
                    )
                    BottomBarItem(
                        icon = Icons.Default.MoreVert,
                        text = "More",
                        isActive = true,
                        onClick = { /* Handle More click */ }
                    )
                }
            }
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun MoreScreenPreview() {

}