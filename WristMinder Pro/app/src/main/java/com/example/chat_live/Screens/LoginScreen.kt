package com.example.chat_live.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat_live.CLViewModel
import com.example.chat_live.CheckSignedIn
import com.example.chat_live.CommonProgressBar
import com.example.chat_live.DestinationScreen
import com.example.chat_live.R
import com.example.chat_live.navigateTo
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController,vm:CLViewModel){
  var Resetemail = remember { mutableStateOf(TextFieldValue("")) }
  var showDialog by remember { mutableStateOf(false) }
  CheckSignedIn(vm=vm,navController=navController)
  Box(modifier = Modifier.fillMaxSize()
    .background(Color(0xFFD2C7FF))
    .padding(horizontal = 20.dp),

  ) {

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
      Image(
        painter = painterResource(id = R.drawable.speak),
        contentDescription = "",
        modifier = Modifier
          .width(200.dp)
          .padding(top = 16.dp)
          .padding(8.dp)
      )


      Text(
        text = "Login",
        style = TextStyle(
          fontSize = 24.sp,
          fontFamily = FontFamily.SansSerif,
          fontWeight = FontWeight.Bold,

        ),
        color = Color(0xFF455A64),
        modifier = Modifier.padding(bottom = 16.dp)
      )



      // Number TextField
      var number = remember { mutableStateOf(TextFieldValue()) }
      OutlinedTextField(
        value = number.value,
        onValueChange = { number.value = it },
        label = { Text("Number / Email ") },
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

      // LOGIN Button
      Button(
        onClick = {
          vm.LoginIN(number.value.text,password.value.text)

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
          text = "Login",
          color = Color.Black,
          style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
        )
      }
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("New here?",
          color = Color(0xFF455A64),)
        TextButton(
          onClick =  {navigateTo(navController, DestinationScreen.Signup.route)},
        ) {
          Text("SignUp",
            color = Color(0xFF455A64),style = TextStyle(
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            ))
        }
      }


      // Forgot Password
      Text(
        text = "Forgot Password?",
        color = Color(0xFF6200EA), // Use a trendy color for accent
        modifier = Modifier
          .padding(top = 16.dp, bottom = 32.dp) // Add padding for spacing
          .clickable {
            showDialog = true


          },
        style = TextStyle(
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          textDecoration = TextDecoration.Underline // Add underline for emphasis
        )
      )
    }
  }

  if (vm.inProcess.value){
    CommonProgressBar()
  }
  if (vm.inprogressPasswordreset.value){
    CommonProgressBar()
  }


  @Composable
  fun ForgotPasswordDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
  ) {

    AlertDialog(
      onDismissRequest = onDismissRequest,
      title = { Text("Reset Password") },
      text = {
        Column {
          Text("Enter your email address to receive a password reset link.")
          OutlinedTextField(
            value = Resetemail.value,
            onValueChange = { Resetemail.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            onConfirm(Resetemail.value.text)
            onDismissRequest()
          }
        ) {
          Text("OK")
        }
      },
      dismissButton = {
        Button(onClick = onDismissRequest) {
          Text("Cancel")
        }
      }
    )
  }
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  if (showDialog) {
    ForgotPasswordDialog(
      onDismissRequest = { showDialog = false },
      onConfirm = { email ->
        if (email.isEmpty()){
          Toast.makeText(context, "Empty Found", Toast.LENGTH_SHORT).show()
        }
        else{
          coroutineScope.launch {
           var bol= vm.passwordReset(email)
            if(bol){
              Toast.makeText(context, "Password Reset Link sent successfully", Toast.LENGTH_SHORT).show()
            }
            else{
              Toast.makeText(context, "Failed to send Password Reset Link", Toast.LENGTH_SHORT).show()
            }
          }



        }
      }
    )
  }


}