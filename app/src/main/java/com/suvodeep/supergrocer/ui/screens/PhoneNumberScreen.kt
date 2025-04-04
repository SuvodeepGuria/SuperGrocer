package com.suvodeep.supergrocer.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.suvodeep.supergrocer.R
import com.suvodeep.supergrocer.SuperGrocerViewModel
import com.suvodeep.supergrocer.auth
import java.util.concurrent.TimeUnit

@Composable
fun PhoneNumberScreen(phoneNo: String,
                      superGrocerViewModel: SuperGrocerViewModel,
                      callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks){
    val context= LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.phoneverification))
    Column(modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            composition = composition, modifier = Modifier.size(200.dp).clip(RoundedCornerShape(CornerSize(200.dp))), iterations = 1000)
        Text("Enter your mobile number to proceed", fontSize = 25.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Text("This phone number will be used for the purpose of all communication. You shall receive an SMS with a code of verification",
            fontSize = 10.sp, color = Color(103, 100, 100, 255),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), textAlign = TextAlign.Center
        )
        TextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
            value = phoneNo,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = {
                superGrocerViewModel.updatePhoneNo(it)
            },
            label = {
                Text("Phone Number")
            },
            leadingIcon = {Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone Icon")},
            singleLine = true
        )
        Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), onClick = {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91"+phoneNo) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(context as Activity) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }) {
            Text("Send OTP")
        }
    }
}