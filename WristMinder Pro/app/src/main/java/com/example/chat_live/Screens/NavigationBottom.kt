package com.example.chat_live.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chat_live.DestinationScreen
import com.example.chat_live.R
import com.example.chat_live.navigateTo

enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationScreen) {

    CHATlIST(R.drawable.talk, DestinationScreen.ChatList),
    STATUSlIST(R.drawable.loading, DestinationScreen.StatusList),
    PROFILElIST(R.drawable.profile, DestinationScreen.Profile)
}

@Composable
fun NavigationBottom(
    SelectedItem: BottomNavigationItem,
    navController: NavController
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        for (item in  BottomNavigationItem.values() ){
            Image(painter = painterResource(id = item.icon), contentDescription = null
            , modifier = Modifier.size(40.dp).weight(1f).clickable{navigateTo(navController,item.navDestination.route)}
                    .then(
                        if (item == SelectedItem) Modifier.shadow(elevation = 10.dp, shape = MaterialTheme.shapes.extraLarge)


                        else Modifier
                    )
            , colorFilter = if(item==SelectedItem)
                    null
                else    null
            )
        }

    }


}