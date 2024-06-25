package com.example.chat_live

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_live.Screens.ChatScreenList
import com.example.chat_live.Screens.DownloadReport
import com.example.chat_live.Screens.HelpScreen
import com.example.chat_live.Screens.InviteScreen
import com.example.chat_live.Screens.LoginScreen
import com.example.chat_live.Screens.MoreScreen
import com.example.chat_live.Screens.OnboardingScreen
import com.example.chat_live.Screens.Profile
import com.example.chat_live.Screens.SignupScreen
import com.example.chat_live.Screens.SingleChatScreen
import com.example.chat_live.Screens.SingleStatus
import com.example.chat_live.Screens.SplashScreenn
import com.example.chat_live.Screens.StatusScreen
import com.example.chat_live.Screens.UploadScreen
import com.example.chat_live.ui.theme.ChatLiveTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

sealed class DestinationScreen(var route :String){
    object Signup:DestinationScreen("signup")
    object Login:DestinationScreen("login")
    object Profile:DestinationScreen("profile")
    object ChatList:DestinationScreen("chatlist")
    object UploadScreen:DestinationScreen("UploadScreen")
    object ScanScreen:DestinationScreen("ScanScreen")
    object MoreScreen:DestinationScreen("more")
    object DownloadReport:DestinationScreen("DownloadReport")
    object SplashScreenn:DestinationScreen("SplashScreenn")
    object SingleChat:DestinationScreen("singlechat/{chatId}"){
        fun createRoute(id:String)="singlechat/$id"
    }
    object StatusList:DestinationScreen("statusList")
    object SingleStatus:DestinationScreen("singleStatus/{userId}"){
        fun createRoute(userid:String)="singleStatus/$userid"
    }


}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatLiveTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    fun ChatAppNavigation(){
        val navController= rememberNavController()
        var vm=hiltViewModel<CLViewModel>()
        NavHost(navController = navController,startDestination = "SplashScreen"){
            composable("SplashScreen") {
                SplashScreen(navController = navController,onSignUpClick = {
                    navController.navigate("SplashScreen")
                })
            }
            composable(DestinationScreen.Signup.route){
                SignupScreen(navController,vm)
            }
            composable(DestinationScreen.Login.route){
                LoginScreen(navController,vm)
            }
            composable(DestinationScreen.ChatList.route){
                ChatScreenList(navController,vm)
            }
            composable(DestinationScreen.Profile.route){
                Profile(navController,vm)
            }
            composable(DestinationScreen.Signup.route){
                SignupScreen(navController,vm)
            }
            composable(DestinationScreen.StatusList.route){
                StatusScreen(navController,vm)
            }
            composable("Onboard"){
                OnboardingScreen(navController)
            }
            composable(DestinationScreen.DownloadReport.route){
                DownloadReport(navController,vm)
            }
            composable(DestinationScreen.UploadScreen.route){
                UploadScreen(navController,vm)
            }
            composable(DestinationScreen.MoreScreen.route){
                MoreScreen(navController,vm)
            }
            composable(DestinationScreen.SplashScreenn.route){
                SplashScreenn(navController)
            }
            composable("invite"){
                InviteScreen(navController)
            }
            composable("help"){
                HelpScreen(navController)
            }
            composable(DestinationScreen.SingleStatus.route){

                 val userId=it.arguments?.getString("userId")
                userId?.let {
                    SingleStatus(navController,vm,userId= userId)
                }

            }
            composable(DestinationScreen.SingleChat.route){
                val chatId=it.arguments?.getString("chatId")
                Log.e("Chat-Live Application",chatId.toString())
                chatId?.let{
                    SingleChatScreen(navController, vm,chatId=chatId)
                }
                }


        }


    }

}
@Composable
fun SplashScreen(navController: NavController, onSignUpClick: () -> Unit){
    LaunchedEffect(Unit) {

        delay(3000) // Wait for 3 seconds

        navController.navigate(DestinationScreen.Login.route)

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

