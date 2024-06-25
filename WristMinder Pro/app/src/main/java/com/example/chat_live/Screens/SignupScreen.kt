package com.example.chat_live.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_live.CLViewModel
import com.example.chat_live.CheckSignedIn
import com.example.chat_live.CommonProgressBar
import com.example.chat_live.DestinationScreen
import com.example.chat_live.R
import com.example.chat_live.navigateTo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController, vm: CLViewModel) {

    CheckSignedIn(vm=vm,navController=navController)
    Box(modifier = Modifier.fillMaxSize()
        .background(Color(0xFFD2C7FF))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val focus = LocalFocusManager.current
            Image(
                painter = painterResource(id = R.drawable.speak),
                contentDescription = "",
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )

            Text(
                text = "Signup",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Name TextField
            val name = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Name") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Magenta,
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.LightGray,
                )
            )

            // Number TextField
            var number = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = number.value,
                onValueChange = { number.value = it },
                label = { Text("Number") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Magenta,
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.LightGray,
                )
            )

            // Email TextField
            var email = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Magenta,
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.LightGray,
                )
            )

            // Password TextField
            var password = remember { mutableStateOf(TextFieldValue()) }
            var passwordVisibility = remember { mutableStateOf(false) }

            val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                ,
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                visualTransformation = visualTransformation,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Magenta,
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.LightGray,
                )
                ,
                trailingIcon ={
                    IconButton(
                        onClick = { passwordVisibility.value = passwordVisibility.value.not() }
                    ) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (passwordVisibility.value) "Hide password" else "Show password",
                            tint = Color.Gray
                        )
                    }
                }
            )

            // Remember Me Checkbox
            var rememberMe = remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe.value,
                    onCheckedChange = {
                        rememberMe.value = it

                    },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = CheckboxDefaults.colors(Color.Magenta)
                )
                Text(
                    text = "Remember Me",
                    modifier = Modifier.clickable {
                        rememberMe.value = rememberMe.value.not()
                    },
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                )
            }

            // Signup Button
            Button(
                onClick = {
                    vm.SignUps(navController, name.value.text,
                        number.value.text, email.value.text,
                        password.value.text)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEAE5FF),
                    contentColor = Color(0xFF455A64)
                )
            ) {
                Text(
                    text = "Signup",
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color(0xFF455A64),
                )
                TextButton(
                    onClick = {

                        navigateTo(navController, DestinationScreen.Login.route)
                    } // Navigate to LoginScreen
                ) {
                    Text(
                        text = "Login",
                        color = Color(0xFF455A64),
                    )
                }
            }

        }
    }

    if (vm.inProcess.value){
        CommonProgressBar()
    }

}


