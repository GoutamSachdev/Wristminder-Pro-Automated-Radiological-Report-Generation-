package com.example.chat_live.Screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_live.CLViewModel
import com.example.chat_live.DestinationScreen
import com.example.chat_live.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun rememberInsetsPaddingValues(): PaddingValues {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    val statusBarHeight = 24.dp
    val navigationBarHeight = 70.dp

    return PaddingValues(
        top = statusBarHeight + 56.dp,
        start = 0.dp,
        end = 0.dp,
        bottom = navigationBarHeight
    )
}
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreenList(navController: NavController, vm: CLViewModel) {
    val insetsPaddingValues = rememberInsetsPaddingValues()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFFD2C7FF))
            )
        },
        floatingActionButton ={
        FloatingActionButton(
            onClick = {
               navController.navigate("Onboard")
            }, containerColor = MaterialTheme.colorScheme.secondary,
            shape = CircleShape,
            modifier = Modifier.padding(bottom = 40.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add", tint = Color.White
            )
        }},
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFFD2C7FF))
                    .padding(insetsPaddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "WRIST MINDER PRO: \nINJURY REPORT GENERATION",
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF455A64)),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("ScanScreen")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFFB4ACD3))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Filled.Search, contentDescription = "Scan", tint = Color(0xFF000040))
                                Text("Scan", style = TextStyle(fontSize = 12.sp, color = Color(0xFF455A64)))
                            }
                        }
                        Button(
                            onClick = {
                                navController.navigate("uploadscreen")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFFB4ACD3))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "Upload", tint = Color(0xFF000040))
                                Text("Upload", style = TextStyle(fontSize = 12.sp, color = Color(0xFF455A64)))
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                    HomeCard(
                        image = painterResource(id = com.example.chat_live.R.drawable.softtissues),
                        title = "SOFT TISSUE SWELLING",
                        description = "Patient containing swelling in soft tissues"
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                }
                item {
                    HomeCard(
                        image = painterResource(id = com.example.chat_live.R.drawable.infetion),
                        title = "WRIST INFECTION",
                        description = "After healing does any infection still exist"
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                }
                item {
                    HomeCard(
                        image = painterResource(id = com.example.chat_live.R.drawable.fraction),
                        title = "FRACTURE",
                        description = "Does patient have fracture or not?"
                    )
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))
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
                        isActive = true,
                        onClick = {  }
                    )
                    BottomBarItem(
                        icon = Icons.Default.List,
                        text = "Activity",
                        isActive = false,
                        onClick = { navigateTo(navController, DestinationScreen.UploadScreen.route) }
                    )
                    BottomBarItem(
                        icon = Icons.Default.MoreVert,
                        text = "More",
                        isActive = false,
                        onClick = { navigateTo(navController, DestinationScreen.MoreScreen.route)}
                    )
                }
            }
        }
    )

}

@Composable
fun HomeCard(
    image: Painter,
    title: String,
    description: String
) {
    Card(
        elevation = CardDefaults.cardElevation(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(Color(0XFFBEB6DB))
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(width = 100.dp, height = 100.dp)
                    .scale(2f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(36.dp))
            Spacer(modifier = Modifier.weight(1f))
            Column (verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = title, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF455A64)), textAlign = TextAlign.Center)
                Text(text = description, style = TextStyle(color = Color(0xFF455A64)), textAlign = TextAlign.Center)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAB(
    showDialog: Boolean,
    onFabClick: () -> Unit,
    onDismiss: () -> Unit,
    onAddChat: (String) -> Unit
) {

    val addChatMember = remember {
        mutableStateOf("")
    }

    if (showDialog) {
        AlertDialog(onDismissRequest = {
            onDismiss.invoke()
            addChatMember.value = ""
        },
            confirmButton = {
                Button(onClick = { onAddChat(addChatMember.value) }) {
                    Text(text = "Add chat")

                }


            },
            title = { Text(text = "Add Chat") },
            text = {
                OutlinedTextField(
                    value = addChatMember.value,
                    onValueChange = { addChatMember.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )


    }
    FloatingActionButton(
        onClick = {
            onFabClick.invoke()
        }, containerColor = MaterialTheme.colorScheme.secondary,
        shape = CircleShape,
        modifier = Modifier.padding(bottom = 40.dp)

    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add", tint = Color.White
        )
    }

}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val color = if (isActive) Color.Blue else Color.Gray
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            style = TextStyle(fontSize = 12.sp, color = color)
        )
    }
}

