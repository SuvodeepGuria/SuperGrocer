package com.suvodeep.supergrocer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.suvodeep.supergrocer.R
import com.suvodeep.supergrocer.SuperGrocerAppScreens
import com.suvodeep.supergrocer.SuperGrocerViewModel
import com.suvodeep.supergrocer.auth

@Composable
fun AccountScreen(
    navController: NavController, superGrocerViewModel: SuperGrocerViewModel,
    /*superGrocerViewModel: SuperGrocerViewModel*/) {
    val logOutState=superGrocerViewModel.onclickLogOut.collectAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.profile))
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LottieAnimation(composition = composition,
                modifier = Modifier.padding(top = 15.dp).size(150.dp)
                    .clip(RoundedCornerShape(CornerSize(200.dp)))
                    .background(Color.Green.copy(0.2f)),
                iterations = 10000,
                speed = 0.3f)
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp) // Adds spacing between items
            ) {
                AccountOption(title = "Add Address", onClick = {navController.navigate(
                    SuperGrocerAppScreens.Address.name) })
                AccountOption(title = "Your Orders", onClick = {navController.navigate(
                    SuperGrocerAppScreens.YourOrder.name)})
                AccountOption(title = "Log Out", onClick = {
                    superGrocerViewModel.setLogOutState(true)
//                    auth.signOut()
//                    superGrocerViewModel.clearData()
                })
            }
            if(logOutState.value){
                AlertMessage(onYesClicked = {
//                    navController.navigate(SuperGrocerAppScreens.Login.name)
                    superGrocerViewModel.setLogOutState(false)
                    auth.signOut()
                    superGrocerViewModel.clearData()
                },onNoClicked = {
                    superGrocerViewModel.setLogOutState(false)
                })
            }
        }
    }
}

@Composable
fun AccountOption(title: String, onClick: () -> Unit) {
    Surface(
        color = Color.Gray.copy(0.1f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Fixes alignment
        ) {
            Text(
                title,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.KeyboardDoubleArrowRight,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun AlertMessage(onYesClicked: () -> Unit, onNoClicked: () -> Unit) {
    val context= LocalContext.current
    AlertDialog(
        title = {
            Text("Log Out?", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        },
        text = {
            Text("Are you sure you want to log out?")
        },
        confirmButton = {
            TextButton(onClick = { onYesClicked()
                Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
            }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = { onNoClicked() }) {
                Text("No")
            }
        },
        onDismissRequest = { onNoClicked() },
    )
}
