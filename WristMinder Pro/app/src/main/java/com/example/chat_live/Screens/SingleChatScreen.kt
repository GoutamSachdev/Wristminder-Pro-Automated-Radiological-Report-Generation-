package com.example.chat_live.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.chat_live.CLViewModel
import com.example.chat_live.Data.ChatUser
import com.example.chat_live.Data.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChatScreen(navController: NavController, vm: CLViewModel, chatId: String) {
    var reply by rememberSaveable {
        mutableStateOf("")
    }
   val onSendReply={
       vm.onSendReply(chatId,reply)
        reply=""
    }
    var showDialog by remember { mutableStateOf(false) }
    val myUser = vm.userData.value
    val currentChat=vm.chats.value.first{it.chatId==chatId}

    val chatUser= if (myUser?.userId==currentChat.user1.userId) currentChat.user2 else currentChat.user1
    var chatMessage=vm.chatMessage
    LaunchedEffect(key1 = Unit ){
        vm.populateMessage(chatId)

    }
    BackHandler{
     vm.depopulatedMessage()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Column(modifier = Modifier.clickable{
                        showDialog = true
                    }) {
                        ChatHeader(chatUser.name ?: "", chatUser.imageUrl ?: "") {
                            navController.popBackStack()
                            vm.depopulatedMessage()
                        }

                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {

                ReplyBox(
                    reply,   {reply=it},onSendReply=onSendReply
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                MessageBox( modifier=Modifier.weight(1f),chatMessage.value,myUser?.userId?:"")

            }
        }
    )
    if (showDialog) {
        UserDetailsDialog(chatUser) {
            showDialog = false
        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyBox(
    reply: String,
    onReplyChange: (String) -> Unit,
    onSendReply: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // TextField for typing the reply
        OutlinedTextField(
            value = reply,
            onValueChange = { onReplyChange(it) },
            maxLines = 3,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(text = "Type your reply...") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = { onSendReply() }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray, // Adjust colors as needed
                unfocusedBorderColor = Color.Gray
            )
        )

        // Send button
        IconButton(
            onClick = { onSendReply() },
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send Reply",
                tint = Color.Gray // Adjust color as needed
            )
        }
    }
}
@Composable
fun MessageBox(modifier: Modifier, chatMessage: List<Message>, currentUserId: String) {
    LazyColumn(modifier = modifier) {
        items(chatMessage) { msg ->
            val alignment = if (msg.sendBy == currentUserId) Alignment.End else Alignment.Start
            val backgroundColor = if (msg.sendBy == currentUserId) Color(0xFF68c400) else Color(0xFFc0c0c0)
            val alignmentBox=if (msg.sendBy == currentUserId) Alignment.CenterEnd else Alignment.CenterStart
            val alignDate= if(msg.sendBy == currentUserId)Arrangement.End else Arrangement.Start
            Column(
                horizontalAlignment = alignment,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = alignmentBox
                ) {
                    Surface(
                        color = backgroundColor,
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = msg.message ?: "",
                            style = TextStyle(color = Color.White),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                Row(
                    horizontalArrangement = alignDate,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(msg.timestamp)),
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun UserDetailsDialog(chatUser: ChatUser, onClose: () -> Unit) {
    Dialog(onDismissRequest = { onClose() }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!chatUser.imageUrl.isNullOrEmpty()) {
                        Image(
                            painter = rememberImagePainter(data = chatUser.imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        val initials = chatUser.name?.split(" ")?.mapNotNull { it.firstOrNull()?.toString() }?.take(2)?.joinToString("") ?: "NN"
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = initials,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Name",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = chatUser.name ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Phone",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = chatUser.number ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onClose() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Close")
                }
            }
        }
    }
}


@Composable
fun ChatHeader(
    name: String,
    imageUrl: String,
    onback:()->Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Back button
        IconButton(
            onClick = { onback.invoke() },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface // Use theme color for consistency
            )
        }

        // User image or initials
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .border(1.dp, MaterialTheme.colorScheme.onSurface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl.isNotBlank()) {
                Image(
                    painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {

                            error(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "User Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Display user initials if image is not available
                Text(
                    text = name.take(2).toUpperCase(),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        // User name
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface // Use theme color for consistency
        )
    }
}

