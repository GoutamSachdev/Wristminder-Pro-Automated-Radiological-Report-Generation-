package com.example.chat_live.Screens

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chat_live.CLViewModel
import com.example.chat_live.CommonDivider
import com.example.chat_live.CommonImage
import com.example.chat_live.CommonProgressBar
import com.example.chat_live.DestinationScreen
import com.example.chat_live.navigateTo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController, vm: CLViewModel) {
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
    if (inProcess) {
        CommonProgressBar()
    } else {
        Scaffold(
            topBar = {  },
            bottomBar = {  BottomAppBar(
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
                        isActive = false,
                        onClick = { navigateTo(navController, DestinationScreen.StatusList.route) }
                    )
                    BottomBarItem(
                        icon = Icons.Default.MoreVert,
                        text = "More",
                        isActive = true,
                        onClick = {  navigateTo(navController = navController, route = DestinationScreen.MoreScreen.route) }
                    )
                }
            }
            },
            content = { it->


                Column(modifier = Modifier.padding(it).background(Color(0XFFD2C7FF)).fillMaxWidth().fillMaxHeight()) {
                ProfileDetail(modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                    vm = vm,
                    name = name.value,
                    number = number.value,
                    onNameChange = { name.value = it },
                    onNumberChange = { number.value = it },
                    onSave = {
                        vm.createOrUpdateProfile(name = name.value, number = number.value)
                    },
                    onBack = {
                        navigateTo(
                            navController = navController, route = DestinationScreen.MoreScreen.route
                        )
                    },
                    onLogOut = {
                        vm.logout()
                        navigateTo(navController = navController, route = DestinationScreen.Login.route)
                        Toast.makeText(context, "LogOut SuccesfullY: ", Toast.LENGTH_SHORT).show()
                    }


                )

            } }
        )



    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetail(
    modifier: Modifier,
    name: String,
    number: String,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onLogOut: () -> Unit,
    vm: CLViewModel
) {
    val imageUrl = vm.userData.value?.imageUrl

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0XFFD2C7FF)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack.invoke() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() })
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Profile",
                color = Color(0XFF455A64),
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Save", modifier = Modifier.clickable { onSave.invoke() })
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onSave.invoke() }) {
                    Icon(Icons.Default.Save, contentDescription = "Save")
                }
            }
        }

        CommonDivider()
        ProfileImage(imageUrl = imageUrl, vm = vm)
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = name, onValueChange = onNameChange, colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Number", modifier = Modifier.width(100.dp))
            TextField(
                value = number, onValueChange = onNumberChange, colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onLogOut.invoke() },
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0XFFEAE5FF),
                    contentColor = Color(0XFF455A64)
                ) // Adding some padding to the button
            ) {
                Text(text = "Log Out")
            }
        }
        CommonDivider()
    }


}

@Composable
fun TopHeader(){

}
@Composable
fun ProfileImage(imageUrl: String?, vm: CLViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                vm.uploadProfileImage(it)
                Toast.makeText(context, "Profile Picture Updated", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(context, "Profile Picture not Updated: ", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    launcher.launch(intent)
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier.padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                ) {
                    CommonImage(data = imageUrl)
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Change Profile Picture",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }

    }
    if (vm.inProcess.value) {
        CommonProgressBar()
    }
}



